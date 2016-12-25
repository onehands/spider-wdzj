package com.stbbd.spider.wdzj.task.intercept;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;
import com.stbbd.spider.wdzj.domain.P2PPlatform;
import org.apache.http.conn.HttpHostConnectException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 朱国印 on 16-12-9.
 */
public class PingjiInterceptor extends FalsifyingWebConnection {

    private final static Logger LOGGER = LoggerFactory.getLogger(PingjiInterceptor.class);

    public PingjiInterceptor(WebClient webClient) throws IllegalArgumentException {
        super(webClient);
    }

    @Override
    public WebResponse getResponse(WebRequest request) throws IOException {
        WebResponse webResponse;
        try {
            webResponse = super.getResponse(request);
        } catch (HttpHostConnectException e) {
            LOGGER.error("创建response失败 即将切换代理", e);
            throw e;
        }
        if (request.getUrl().toString().startsWith("http://www.wdzj.com/pingji.html")) {
            List<P2PPlatform> p2PPlatforms = new ArrayList<P2PPlatform>();
            String html = webResponse.getContentAsString();
            Document document = Jsoup.parse(html);
            Elements elements = document.getElementsByClass("mod-tablelists");
            Element element = elements.first();
            Elements tableElements = element.getElementsByTag("table");
            Element tableElement = tableElements.first();
            Elements tbodyElements = tableElement.getElementsByTag("tbody");
            Element tbodyElement = tbodyElements.first();
            for (Element trElement : tbodyElement.getElementsByTag("tr")) {
                P2PPlatform p2PPlatform = new P2PPlatform();
                p2PPlatform.setId(Long.parseLong(trElement.attr("data-platId")));
                Elements tdElements = trElement.getElementsByTag("td");
                Element rankElement = tdElements.get(0);
                Elements divElement = rankElement.getElementsByTag("div");
                String rankStr = divElement.first().text();
                p2PPlatform.setRank(Integer.parseInt(rankStr));
                Element platformElement = tdElements.get(1);
                Elements pfDivElement = platformElement.getElementsByTag("div");
                p2PPlatform.setPlatform(pfDivElement.get(0).text());
                p2PPlatform.setFzzs(tdElements.get(2).text());
                p2PPlatform.setSxsj(tdElements.get(3).text());
                p2PPlatform.setSzcs(tdElements.get(4).text());
                p2PPlatform.setCj(tdElements.get(5).text());
                p2PPlatform.setRq(tdElements.get(6).text());
                p2PPlatform.setGg(tdElements.get(7).text());
                p2PPlatform.setFsd(tdElements.get(8).text());
                p2PPlatform.setLdx(tdElements.get(9).text());
                p2PPlatform.setTmd(tdElements.get(10).text());
                p2PPlatforms.add(p2PPlatform);
            }
            System.out.println(p2PPlatforms);
        } else {
            return createWebResponse(request, "", "text/html", 200, "Ok");
        }
        return webResponse;
    }
}
