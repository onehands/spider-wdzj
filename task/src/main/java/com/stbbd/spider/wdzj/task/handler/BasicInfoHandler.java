package com.stbbd.spider.wdzj.task.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.framewark.utils.HttpClientUtils;
import com.stbbd.spider.wdzj.entities.PlatInfo;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import com.stbbd.spider.wdzj.task.entities.BasicInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lei on 16-12-21.
 */
@Component
@Slf4j
public class BasicInfoHandler extends HttpClientHandler {
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.BASICINFO;

    @Override
    public void excute(String url) throws Exception {
        if (!super.locktask(taskType)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("sort", "0");

        for (int i = 1; ; i++) {
            params.put("currPage", i + "");
            log.info("page:" + i);
            String result = HttpClientUtils.doPost(url, params, "UTF-8");
            if (StringUtils.isEmpty(result) || null == handleResult(result)) {
                break;
            }

        }
        releaseTask(taskType);
    }


    public Map<String, Object> handleResult(String result) throws IOException {
        BasicInfoResult sourceOrderList = objectMapper.readValue(result, BasicInfoResult.class);
        if (null == sourceOrderList) {
            return null;
        }
        sourceOrderList.getList().forEach(item -> {
            PlatInfo plat = super.getPlatInfoMapper().findByPlatId(item);
            if (null == plat) {
                super.getPlatInfoMapper().save(item);
            } else {
                BeanUtils.copyProperties(item, plat);
                super.getPlatInfoMapper().updateStatusById(plat);
            }
        });
        if (sourceOrderList.getList().size() > 0) {
            return new HashMap<>();
        }

        return null;
    }

}
