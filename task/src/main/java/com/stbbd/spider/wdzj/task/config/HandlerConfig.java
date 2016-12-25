package com.stbbd.spider.wdzj.task.config;

import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.wdzj.mapper.PlatInfoMapper;
import com.stbbd.spider.wdzj.mapper.SourceDataMapper;
import com.stbbd.spider.wdzj.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by lei on 16-12-21.
 */
@Configuration
public class HandlerConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        HttpClientHandler.setPlatInfoMapper(beanFactory.getBean(PlatInfoMapper.class));
        HttpClientHandler.setTaskMapper(beanFactory.getBean(TaskMapper.class));
        HttpClientHandler.setSourceDataMapper(beanFactory.getBean(SourceDataMapper.class));
    }
}
