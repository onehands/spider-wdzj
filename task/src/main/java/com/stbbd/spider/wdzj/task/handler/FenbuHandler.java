package com.stbbd.spider.wdzj.task.handler;

import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.framewark.utils.HttpClientUtils;
import com.stbbd.spider.wdzj.entities.PlatInfo;
import com.stbbd.spider.wdzj.entities.SourceData;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lei on 16-12-21.
 */
@Component
@Slf4j
public class FenbuHandler extends HttpClientHandler {
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.FENBU;

    @Override
    public void excute(String url) throws Exception {
        if (!locktask(taskType)) {
            return;
        }
        List<PlatInfo> all = super.getPlatInfoMapper().findAll();
        CountDownLatch countDownLatch = new CountDownLatch(all.size());
        log.info("all:" + all.size());
        all.forEach(each -> {
            int id = each.getPlatId();
            try {
                super.pool.execute(new Thread(() -> {
                    try {
                        String newurl = url + id + "html";
                        String result = HttpClientUtils.get(newurl, null);

                        handleResult(result, id, newurl);
                        log.info("id:" + id + "  name  " + each.getPlatName());

                    } catch (Exception e) {
                        log.error("handle exception,", e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }));

            } catch (Exception e) {
                log.error("PLAT_PAGE exception ", e);
            }
        });
        countDownLatch.await();

        super.releaseTask(taskType);
        log.info("done");

    }

    public Map<String, String> handleRequest(String id) throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("platId", id);
        return param;
    }

    public Map<String, Object> handleResult(String result, int platId, String url) throws IOException {
        SourceData platPage = new SourceData();
        platPage.setPlatId(platId);
        platPage.setContent(result);
        platPage.setType(taskType);
        platPage.setUrl(url);
        //保存页面原始信息
        super.getSourceDataMapper().save(platPage);

        //解析数据
        return null;
    }

}
