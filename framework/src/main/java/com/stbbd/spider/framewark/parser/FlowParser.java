package com.stbbd.spider.framewark.parser;

import com.stbbd.spider.framewark.bean.Flow;
import com.stbbd.spider.framewark.bean.Step;
import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by 朱国印 on 16-12-11.
 */
public class FlowParser {

    private static final Logger logger = LoggerFactory.getLogger(FlowParser.class);

    public static Flow parse(InputStream in) {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("flow", Flow.class);
        digester.addObjectCreate("flow/step", Step.class);
        digester.addSetProperties("flow/step");
        digester.addSetNext("flow/step", "addStep", Step.class.getName());
        try {
            return (Flow) digester.parse(in);
        } catch (Exception e) {
            logger.error("解析出错", e);
            throw new RuntimeException("解析出错");
        }
    }
}
