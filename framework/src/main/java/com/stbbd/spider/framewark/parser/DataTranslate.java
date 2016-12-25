package com.stbbd.spider.framewark.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lei on 16-12-24.
 */
@Component
public class DataTranslate {
    private static String TYPE_NAME1 = " http://www.wdzj.com/dangan/";
    private static String TYPE_NAME2 = " http://shuju.wdzj.com/basic-surface-4478html";
    private static String TYPE_NAME3 = " http://shuju.wdzj.com/plat-info-initialize.html";
    private static String TYPE_NAME4 = " http://shuju.wdzj.com/plat-info-target.html";
    private static String TYPE_NAME5 = " http://shuju.wdzj.com/plat-info-";
    private static String TYPE_NAME6 = " http://shuju.wdzj.com/industry-p2pindex.html";
    private static String TYPE_NAME7 = " http://www.wdzj.com/dangan/";
    private static String TYPE_NAME8 = " http://shuju.wdzj.com/wdzj-archives-chart.html?wdzjPlatId=95&type=0&status=0";

    public void translate() {
        //查询所有平台
//        List<PlatInfo> allPlats = companyInfoRepository.findAll();
//        allPlats.forEach(platInfo -> {
//            sourceDataRepository.findByPlatId(platInfo.getPlatId()).forEach(source -> {
//                //按照地址和flag解析，包括html 和原始json
//            });
//        });
    }

}
