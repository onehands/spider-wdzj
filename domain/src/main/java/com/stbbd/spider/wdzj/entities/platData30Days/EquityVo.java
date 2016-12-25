package com.stbbd.spider.wdzj.entities.platData30Days;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by lei on 16-12-22.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquityVo {
    private String insUserId;
    private String insUserName;
    private String insDate;
    private String updUserId;
    private String updUserName;
    private String updDate;
    private int platId;
    private int equityId;
    private String equityMarket;
    private String equityCode;
}
