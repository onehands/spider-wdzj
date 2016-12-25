package com.stbbd.spider.wdzj.task.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stbbd.spider.wdzj.entities.PlatInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by lei on 16-12-21.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicInfoResult {
    List<PlatInfo> list;
}
