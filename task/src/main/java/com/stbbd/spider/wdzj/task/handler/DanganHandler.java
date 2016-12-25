package com.stbbd.spider.wdzj.task.handler;

import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.framewark.utils.HttpClientUtils;
import com.stbbd.spider.wdzj.entities.PlatInfo;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lei on 16-12-21.
 */
@Component
public class DanganHandler extends HttpClientHandler {
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.DANGAN;


    @Override
    public void excute(String url) throws Exception {
        Map<String, String> params = new HashMap<>();
        if (locktask(taskType)) {
            String result = HttpClientUtils.doGet(url, params);
            handleResult(result);
        }
        releaseTask(taskType);
    }

    public Map<String, Object> handleResult(String url) throws IOException {
        List<PlatInfo> sourceOrderList = objectMapper.readValue(url, getCollectionType(ArrayList.class, PlatInfo.class));
        sourceOrderList.forEach(item -> {
            PlatInfo plat = super.getPlatInfoMapper().findByPlatId(item);
            if (null == plat) {
                super.getPlatInfoMapper().save(item);
            } else {
                BeanUtils.copyProperties(item, plat);
                super.getPlatInfoMapper().updateStatusById(plat);
            }

        });

        return null;
    }

}
