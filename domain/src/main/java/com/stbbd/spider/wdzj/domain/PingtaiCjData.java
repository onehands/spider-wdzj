package com.stbbd.spider.wdzj.domain;

/**
 * Created by 朱国印 on 16-12-9.
 */
public class PingtaiCjData implements DBAccessable {
    private long id;
    private String platform;
    private String cjl;
    private String pjsyl;
    private String pjjkqx;
    private String dhye;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCjl() {
        return cjl;
    }

    public void setCjl(String cjl) {
        this.cjl = cjl;
    }

    public String getPjsyl() {
        return pjsyl;
    }

    public void setPjsyl(String pjsyl) {
        this.pjsyl = pjsyl;
    }

    public String getPjjkqx() {
        return pjjkqx;
    }

    public void setPjjkqx(String pjjkqx) {
        this.pjjkqx = pjjkqx;
    }

    public String getDhye() {
        return dhye;
    }

    public void setDhye(String dhye) {
        this.dhye = dhye;
    }

    public String getTableName() {
        return "t_pingtai_cj";
    }
}
