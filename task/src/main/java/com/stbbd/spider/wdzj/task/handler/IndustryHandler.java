package com.stbbd.spider.wdzj.task.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.framewark.utils.HttpClientUtils;
import com.stbbd.spider.wdzj.entities.SourceData;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lei on 16-12-21.
 */
@Component
@Slf4j
public class IndustryHandler extends HttpClientHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.INDUSTRY;

    @Override
    public void excute(String url) throws Exception {
        if (!locktask(taskType)) {
            return;
        }
        try {
            //wdzjPlatId:95
            String result = HttpClientUtils.post(url, this.handleRequest(""));
            handleResult(result, url);
            log.info("IndustryHandler done");

        } catch (Exception e) {
            log.error("handle exception,", e);
        }
        super.releaseTask(taskType);
        log.info("done");

    }

    public Map<String, String> handleRequest(String id) throws Exception {
        Map<String, String> param = new HashMap<>();
        return param;
    }

    public Map<String, Object> handleResult(String result, String url) throws IOException {
        SourceData platPage = new SourceData();
        platPage.setContent(result);
        platPage.setType(SpiderTask.TaskType.INDUSTRY);
        platPage.setUrl(url);
        super.getSourceDataMapper().save(platPage);
        //解析数据
        return null;
    }

}
