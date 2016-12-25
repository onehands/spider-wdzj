package com.stbbd.spider.wdzj.entities.platData30Days;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stbbd.spider.wdzj.deserializer.DateDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by lei on 16-12-22.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatOuterVo {
    private String platCode;
    private String platId;
    private String platName;
    private String aliasName;
    private String platNamePin;
    private String locationArea;
    private String locationAreaName;
    private String locationCity;
    private String locationCityName;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate onlineDate;
    private int registeredCapital;
    private int actualCapital;
    private String officeAddress;
    private String serviceTel;
    private String servicePhone;
    private String companyName;
    private String juridicalPerson;
    private String recordId;
    private String recordLicId;
    private String startInvestmentAmout;
    private String platLogoUrl;
    private String platIconUrl;
    private String creditLevel;
    private String platStatus;
    private String platEarnings;
    private String platEarningsCode;
    private String term;
    private String termCodes;
    private double termWeight;
    private String securityModel;
    private String securityModelCode;
    private String autoBid;
    private String autoBidCode;
    private String newTrustCreditor;
    private int newTrustCreditorCode;
    private int trustCreditorMonth;
    private String businessType;
    private String securityModelOther;
    private String drawScore;
    private String serviceScore;
    private String delayScore;
    private String experienceScore;
    private String drawScoreDetail;
    private String serviceScoreDetail;
    private String delayScoreDetail;
    private String experienceScoreDetail;
    private String bankFunds;
    private String bankCapital;
    private String riskFunds;
    private String riskCapital;
    private String fundCapital;
    private String bidSecurity;
    private String gruarantee;
    private int platBackground;
    private String platBackgroundDetail;
    private String platBackgroundMark;
    private String platUrl;
    private String appDownloadLink;
    private String trustFunds;
    private String trustCapital;
    private String trustCreditor;
    private int riskReserve;
    private String rechargeExpense;
    private String rechargeExpenseDetail;
    private String manageExpense;
    private String manageExpenseDetail;
    private String withdrawExpense;
    private String withdrawExpenseDetail;
    private String vipExpense;
    private String vipExpenseDetail;
    private String transferExpense;
    private String transferExpenseDetail;
    private String payment;
    private String paymode;
    private int riskcontrol;
    private int association;
    private int credit;
    private int inspection;
    private int problem;
    private int platEquity;
//    private List<EquityVo> equityVoList;
    private String riskcontrolDetail;
    private String associationDetail;
    private int withTzj;
    private String tzjPj;
    private String showShuju;
    private String gjlhhFlag;
    private String gjlhhTime;
    private String bindingFlag;
//    private int problemTime;

//    private List<InspectionVo> inspectionVoList;


}
