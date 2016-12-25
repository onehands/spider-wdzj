package com.stbbd.spider.wdzj.task.handler;

import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.framewark.utils.HttpClientUtils;
import com.stbbd.spider.wdzj.entities.PlatInfo;
import com.stbbd.spider.wdzj.entities.SourceData;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
public class LilvHandler extends HttpClientHandler {
    private SpiderTask.TaskType taskType = SpiderTask.TaskType.LILV;

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
                        //i代表数据类型：0:利率/成交量  1:历史待还/净流入  3：投资人数 / 借款人数
                        //j代表维度：0:日  1:周  2：月，  投资人数只有 日
                        for (int i = 0; i < 4; i++) {
                            if (i == 2) {
                                continue;
                            }
                            for (int j = 0; j < 3; j++) {
                                if (i == 3 && j != 1) {
                                    break;
                                }
                                String newurl = url + "?" + "wdzjPlatId=" + id + "&type=" + i + "&status=" + j;// wdzjPlatId=95&type=1&status=3;
                                //http://shuju.wdzj.com/wdzj-archives-chart.html?wdzjPlatId=95&type=1&status=3
                                Map<String, String> paramMap = new HashMap<>();
                                //wdzjPlatId=95&type=0&status=1
                                paramMap.put("type", i + "");
                                paramMap.put("status", j + "");
                                paramMap.put("wdzjPlatId", id + "");
                                String result = HttpClientUtils.get(url, paramMap);
                                if (!StringUtils.isEmpty(result)) {
                                    handleResult(result, id, newurl, i + "|" + j);
                                }

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

    public Map<String, String> handleRequest(String id) throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("platId", id);
        return param;
    }

    public Map<String, Object> handleResult(String result, int platId, String url, String flag) throws IOException {
        SourceData platPage = new SourceData();
        platPage.setPlatId(platId);
        platPage.setContent(result);
        platPage.setType(SpiderTask.TaskType.LILV);
        platPage.setUrl(url);
        platPage.setFlag(flag);
        super.getSourceDataMapper().save(platPage);
        //解析数据
        return null;
    }

}
