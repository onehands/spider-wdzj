package com.stbbd.spider.wdzj.entities.platData30Days;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by lei on 16-12-22.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatData30Days {
    private Plat30DaysData data;
    private int platId;
}
