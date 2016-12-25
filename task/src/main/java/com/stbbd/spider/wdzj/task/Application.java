package com.stbbd.spider.wdzj.task;

import com.stbbd.spider.framewark.bean.Flow;
import com.stbbd.spider.framewark.exec.DefaultFlowExecutor;
import com.stbbd.spider.framewark.parser.FlowParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.InputStream;

/**
 * Created by lei on 16-12-20.
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@ComponentScan(basePackages = {"com.*","com.stbbd.spider.wdzj.mapper"})
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
        DefaultFlowExecutor excutor = app.getBean(DefaultFlowExecutor.class);
        InputStream inputStream = Application.class.getResourceAsStream("/spider-flow.xml");
        Flow flow = FlowParser.parse(inputStream);
        excutor.execute(flow);
    }
}