package com.stbbd.spider.wdzj.task;

import com.stbbd.spider.framewark.bean.Flow;
import com.stbbd.spider.framewark.exec.DefaultFlowExecutor;
import com.stbbd.spider.framewark.exec.FlowExecutor;
import com.stbbd.spider.framewark.parser.FlowParser;

import java.io.InputStream;

/**
 * Created by 朱国印 on 16-12-9.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = Main.class.getResourceAsStream("/spider-flow.xml");
        Flow flow = FlowParser.parse(inputStream);
        FlowExecutor flowExecutor = new DefaultFlowExecutor();
        flowExecutor.execute(flow);
    }
}
