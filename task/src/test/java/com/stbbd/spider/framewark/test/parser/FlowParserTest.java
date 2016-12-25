package com.stbbd.spider.framewark.test.parser;

import com.stbbd.spider.framewark.bean.Flow;
import com.stbbd.spider.framewark.parser.FlowParser;

/**
 * Created by 朱国印 on 16-12-11.
 */
public class FlowParserTest {

    public static void main(String[] args) {
        Flow flow = FlowParser.parse(FlowParserTest.class.getResourceAsStream("/test-flow.xml"));
        System.out.println(flow);
    }
}
