package com.stbbd.spider.wdzj.task.handler;

import com.fasterxml.jackson.databind.JavaType;
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
public class IndexHandler extends HttpClientHandler {
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.INDEX;

    @Override
    public void excute(String url) throws Exception {
        if (!locktask(taskType)) {
            return;
        }
        try {
            //wdzjPlatId:95
            for (int i = 0; i < 7; i++) {
                String targeturl = url + i + "-1.html";
                String result = HttpClientUtils.post(targeturl, this.handleRequest(""));
                handleResult(result, targeturl);
            }
            log.info("index done");

        } catch (Exception e) {
            log.error("handle exception,", e);
        }
        super.releaseTask(taskType);

    }

    public Map<String, String> handleRequest(String id) throws Exception {
        Map<String, String> param = new HashMap<>();
        return param;
    }

    public Map<String, Object> handleResult(String result, String url) throws IOException {
        SourceData platPage = new SourceData();
        platPage.setContent(result);
        platPage.setType(SpiderTask.TaskType.INDEX);
        platPage.setUrl(url);
        //保存页面原始信息
        super.getSourceDataMapper().save(platPage);

        //解析数据
        return null;
    }


    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
