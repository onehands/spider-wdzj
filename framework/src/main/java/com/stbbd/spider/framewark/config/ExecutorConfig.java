package com.stbbd.spider.framewark.config;

import com.stbbd.spider.framewark.enums.StepType;
import com.stbbd.spider.framewark.exec.DefaultFlowExecutor;
import com.stbbd.spider.framewark.exec.HtmlUnitExecutor;
import com.stbbd.spider.framewark.exec.HttpClientExecutor;
import com.stbbd.spider.framewark.exec.StepExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;

/**
 * Created by lei on 16-12-21.
 */
@Configuration
public class ExecutorConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        DefaultFlowExecutor defaultFlowExecutor = beanFactory.getBean(DefaultFlowExecutor.class);
        HashMap<StepType, StepExecutor> stepExecutorMap = new HashMap<>();
        stepExecutorMap.put(StepType.HTML_UNIT, beanFactory.getBean(HtmlUnitExecutor.class));
        stepExecutorMap.put(StepType.HTTP_CLIENT, beanFactory.getBean(HttpClientExecutor.class));
        defaultFlowExecutor.setStepExecutorMap(stepExecutorMap);
    }
}
