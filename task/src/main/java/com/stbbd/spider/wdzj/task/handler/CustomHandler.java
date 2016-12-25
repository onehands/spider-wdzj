package com.stbbd.spider.wdzj.task.handler;

import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.framewark.utils.HttpClientUtils;
import com.stbbd.spider.wdzj.entities.SourceData;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lei on 16-12-21.
 */
@Component
@Slf4j
public class CustomHandler extends HttpClientHandler {
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.CUSTOM;

    @Override
    public void excute(String url) throws Exception {
        if (!locktask(taskType)) {
            return;
        }
        try {
            String result = HttpClientUtils.post(url, this.handleRequest(""));
            handleResult(result, url);
            log.info("custom done");
        } catch (Exception e) {
            log.error("handle exception,", e);
        }

        super.releaseTask(taskType);


    }

    public Map<String, String> handleRequest(String id) throws Exception {
        Map<String, String> param = new HashMap<>();
        //type=0&shujuDate=2016-12-182016-12-18
        param.put("type", "0");
        String data = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        param.put("shujuDate", data + data);
        return param;
    }

    public Map<String, Object> handleResult(String result, String url) throws IOException {
        SourceData platPage = new SourceData();
        platPage.setContent(result);
        platPage.setType(SpiderTask.TaskType.CUSTOM);
        platPage.setUrl(url);
        super.getSourceDataMapper().save(platPage);
        //解析数据
        return null;
    }


}
