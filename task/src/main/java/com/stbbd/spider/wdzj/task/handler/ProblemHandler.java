package com.stbbd.spider.wdzj.task.handler;

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
public class ProblemHandler extends HttpClientHandler {

    private SpiderTask.TaskType taskType = SpiderTask.TaskType.PROBLEM;

    @Override
    public void excute(String url) throws Exception {
        if (!locktask(taskType)) {
            return;
        }
        try {
            String result = HttpClientUtils.get(url + "1.html", this.handleRequest(""));
            handleResult(result, url);
            result = HttpClientUtils.get(url + "list-trend.html", this.handleRequest(""));
            handleResult(result, url);
            log.info("ProblemHandler done");
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
        platPage.setType(SpiderTask.TaskType.PROBLEM);
        platPage.setUrl(url);
        super.getSourceDataMapper().save(platPage);
        //解析数据
        return null;
    }
}
