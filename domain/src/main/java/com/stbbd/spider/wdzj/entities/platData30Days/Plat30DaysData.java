package com.stbbd.spider.wdzj.entities.platData30Days;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by lei on 16-12-22.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plat30DaysData {
    private PlatOuterVo platOuterVo;
    private HashMap<String, Double> platShujuMap;
}
