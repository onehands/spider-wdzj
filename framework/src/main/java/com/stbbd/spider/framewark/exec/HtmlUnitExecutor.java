package com.stbbd.spider.framewark.exec;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.util.UrlUtils;
import com.stbbd.spider.framewark.bean.Step;
import com.stbbd.spider.framewark.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 朱国印 on 16-12-12.
 */
@Component
public class HtmlUnitExecutor implements StepExecutor {

    private static Logger logger = LoggerFactory.getLogger(HtmlUnitExecutor.class);

    public void execute(Step step) {
        WebClient client = new WebClient(BrowserVersion.CHROME);
        Object instance = BeanUtils.createInstance(step.getHandlerClass(), client);
        if (instance == null) {
            logger.error("创建实例出错，请参看日志");
            return;
        }
        if (!(instance instanceof WebConnection)) {
            logger.error("handlerClass不是WebConnection，不能继续执行");
            return;
        }
        client.setWebConnection((WebConnection) instance);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.getOptions().setThrowExceptionOnScriptError(false);
        URL url;
        try {
            url = UrlUtils.toUrlUnsafe(step.getUrl());
        } catch (MalformedURLException e) {
            logger.error("转为安全url时出错", e);
            return;
        }
        WebRequest webRequest = new WebRequest(url,
                client.getBrowserVersion().getHtmlAcceptHeader());
        try {
            client.getPage(client.getCurrentWindow().getTopWindow(), webRequest);
        } catch (IOException e) {
            logger.error("转为安全url时出错", e);
            return;
        }
        client.close();
    }
}
