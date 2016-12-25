package com.stbbd.spider.framewark.exec;

import com.stbbd.spider.framewark.bean.Step;
import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.wdzj.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 朱国印 on 16-12-12.
 */
@Component
@Slf4j
public class HttpClientExecutor implements StepExecutor {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;



    public void execute(Step step) {
        try {
            Object instance = beanFactory.getBean(ClassUtil.getClassName(step.getHandlerClass()));
            if (instance == null) {
                log.error("创建实例出错，请参看日志");
                return;
            }
            if (!(instance instanceof HttpClientHandler)) {
                log.error("handlerClass不是WebConnection，不能继续执行");
                return;
            }
            HttpClientHandler handler = (HttpClientHandler) beanFactory.getBean(instance.getClass());
            handler.excute(step.getUrl());
//            pool.execute(new Thread(() -> {
//                try {
//                    handler.excute(step.getUrl());
//                } catch (Exception e) {
//                    log.error("handle exception,", e);
//                }
//            }));
        } catch (Exception e) {
            log.error("handle exception,", e);
        }
    }


}
