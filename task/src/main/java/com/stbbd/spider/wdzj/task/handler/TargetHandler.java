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
public class TargetHandler extends HttpClientHandler {

    private SpiderTask.TaskType taskType = SpiderTask.TaskType.TARGET;

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
                        /**
                         * "wdzjPlatId:95
                         type:1
                         target1:5
                         target2:6"

                         "wdzjPlatId:95
                         type:2
                         target1:1
                         target2:0"

                         "wdzjPlatId:95
                         type:1
                         target1:9
                         target2:10"

                         */
                        int[][] params = {{1, 5, 6},
                                {1, 10, 23},
                                {1, 21, 22},
                                {1, 19, 20},
                                {1, 7, 8},
                                {1, 1, 0},
                                {1, 2, 0},
                                {3, 16, 1},
                                {3, 16, 2},
                                {3, 17, 1},
                                {3, 17, 2},
                                {1, 4, 3},
                                {1, 9, 10},
                                {1, 12, 11},
                                {3, 18, 1},
                                {3, 18, 2}};


                        for (int i = 0; i < params.length; i++) {
                            int[] param = params[i];
                            if (param[0] == 1) {
                                for (int j = 1; j < 4; j++) {
                                    param[0] = j;
                                    String result = HttpClientUtils.doPost(url, this.handleRequest(id + "", param), "UTF-8");
                                    handleResult(result, id, url, param);
                                }
                            } else {
                                String result = HttpClientUtils.doPost(url, this.handleRequest(id + "", param), "UTF-8");
                                handleResult(result, id, url, param);
                            }
                        }


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


    public Map<String, String> handleRequest(String id, int[] param) throws Exception {
        Map<String, String> returnparam = new HashMap<>();
        returnparam.put("wdzjPlatId", id);
        returnparam.put("type", param[0] + "");
        returnparam.put("target1", param[1] + "");
        returnparam.put("target2", param[2] + "");
        return returnparam;
    }

    public Map<String, Object> handleResult(String result, int platId, String url, int[] param) throws IOException {
        SourceData platPage = new SourceData();
        platPage.setPlatId(platId);
        platPage.setContent(result);
        platPage.setType(SpiderTask.TaskType.TARGET);
        platPage.setUrl(url);
        StringBuilder flag = new StringBuilder();
        for (int i = 0; i < param.length; i++) {
            flag.append(param[i] + "|");
        }
        platPage.setFlag(flag.toString());
        super.getSourceDataMapper().save(platPage);
        //解析数据
        return null;
    }


}
