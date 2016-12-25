package com.stbbd.spider.wdzj.task.handler;

import com.stbbd.spider.framewark.handler.HttpClientHandler;
import com.stbbd.spider.wdzj.domain.PingtaiCjData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 朱国印 on 16-12-12.
 */
public class PingtaiChengjiaoHandler extends HttpClientHandler {
    @Override
    public void excute(String result) throws Exception {

    }

    public Map<String, Object> handleResult(String result) {
        List<PingtaiCjData> pingtaiCjDatas = new ArrayList<PingtaiCjData>();
        Document document = Jsoup.parse(result);
        Element tableEle = document.getElementById("platTableTmpl");
        Elements tableElements = tableEle.getElementsByTag("table");
        Element tableElement = tableElements.first();
        Elements tbodyElements = tableElement.getElementsByTag("tbody");
        Element tbodyElement = tbodyElements.first();
        for (Element trElement : tbodyElement.getElementsByTag("tr")) {
            PingtaiCjData pingtaiCjData = new PingtaiCjData();
            pingtaiCjData.setId(Long.parseLong(trElement.attr("data-platId")));
            Elements tdElements = trElement.getElementsByTag("td");
            Element platformElement = tdElements.get(1);
            Elements pfDivElement = platformElement.getElementsByTag("div");
            pingtaiCjData.setPlatform(pfDivElement.get(0).text());
            pingtaiCjData.setCjl(tdElements.get(2).text());
            pingtaiCjData.setPjsyl(tdElements.get(3).text());
            pingtaiCjData.setPjjkqx(tdElements.get(4).text());
            pingtaiCjData.setDhye(tdElements.get(5).text());
            pingtaiCjDatas.add(pingtaiCjData);
        }
        System.out.println(pingtaiCjDatas);
        return new HashMap<String, Object>();
    }

    public Map<String, String> handleRequest(String result) throws Exception {
        return null;
    }
}
