package com.stbbd.spider.wdzj.domain;

import com.stbbd.spider.wdzj.annotation.DBColumn;

import java.util.Date;

/**
 * Created by 朱国印 on 16-12-9.
 */
public class P2PPlatform implements java.io.Serializable, DBAccessable {

    @DBColumn(id = true, name = "id")
    private long id;

    private int rank;

    private String platform;

    private String fzzs;

    private String sxsj;

    private String szcs;

    private String cj;

    private String rq;

    private String gg;

    private String fsd;

    private String ldx;

    private String tmd;

    private Date grabTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFzzs() {
        return fzzs;
    }

    public void setFzzs(String fzzs) {
        this.fzzs = fzzs;
    }

    public String getSxsj() {
        return sxsj;
    }

    public void setSxsj(String sxsj) {
        this.sxsj = sxsj;
    }

    public String getSzcs() {
        return szcs;
    }

    public void setSzcs(String szcs) {
        this.szcs = szcs;
    }

    public String getCj() {
        return cj;
    }

    public void setCj(String cj) {
        this.cj = cj;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public String getFsd() {
        return fsd;
    }

    public void setFsd(String fsd) {
        this.fsd = fsd;
    }

    public String getLdx() {
        return ldx;
    }

    public void setLdx(String ldx) {
        this.ldx = ldx;
    }

    public String getTmd() {
        return tmd;
    }

    public void setTmd(String tmd) {
        this.tmd = tmd;
    }

    public Date getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(Date grabTime) {
        this.grabTime = grabTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTableName() {
        return "t_pingji";
    }
}
