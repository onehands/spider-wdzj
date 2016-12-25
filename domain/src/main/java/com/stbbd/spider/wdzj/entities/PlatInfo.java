package com.stbbd.spider.wdzj.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stbbd.spider.wdzj.deserializer.DateDeserializer;
import lombok.Data;

import java.time.LocalDate;

/**
 * Created by lei on 16-12-20.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatInfo {

    private String id;
    private String allPlatPin;
    private String platPin;
    private int platStatus;
    private String firstPin;
    private int platEarnings;
    private int zonghezhishuRank;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate onlineDate;
    private String cityName;
    private String platNamePin;
    private String platIconUrl;
    private String gjlhhFlag;
    private String platName;
    private int platId;
    private String tzjPj;
    private int zonghezhishu;

}
