package com.stbbd.spider.wdzj.entities.platData30Days;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stbbd.spider.wdzj.deserializer.DateDeserializer;
import lombok.Data;

import java.time.LocalDate;

/**
 * Created by lei on 16-12-22.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionVo {
    private String insUserName;
    private String insUserId;
    private String insDate;
    private String updUserId;
    private String updUserName;
    private String updDate;
    private String platId;
    private String inspectionId;
    private String inspectionTitle;
    private String inspectionUrl;
    private String inspectionType;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate inspectionDate;
    private int inspectionValidDateNum;
    private int postId;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate postDate;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate invalidDate;
    private String inspectionHidId;
    private int isNewsBobao;
}
