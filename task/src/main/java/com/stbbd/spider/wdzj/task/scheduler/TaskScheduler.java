package com.stbbd.spider.wdzj.task.scheduler;

import com.stbbd.spider.framewark.bean.Flow;
import com.stbbd.spider.framewark.exec.DefaultFlowExecutor;
import com.stbbd.spider.framewark.parser.FlowParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by lei on 16-12-21.
 */

@Component
public class TaskScheduler {

    @Autowired
    DefaultFlowExecutor defaultFlowExecutor;


//    @Scheduled(cron = "0 0 0/1 * * ? ")//每小时一次
    public void initTask() {
        InputStream inputStream = TaskScheduler.class.getResourceAsStream("/spider-flow.xml");
        Flow flow = FlowParser.parse(inputStream);
        defaultFlowExecutor.execute(flow);
    }

}
