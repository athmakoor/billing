package com.ref.bean;

import java.io.Serializable;
import java.util.Map;

public class SubscriptionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String date;
    private String transno;
    private String packageid;
    private String pubid;
    private String clickid;

    public SubscriptionDTO(Map<String, String> requestParams) {
        this.setKey(requestParams.get("key"));
        this.setDate(requestParams.get("date"));
        this.setTransno(requestParams.get("transno"));
        this.setPackageid(requestParams.get("packageid"));
        this.setPubid(requestParams.get("pubid"));
        this.setClickid(requestParams.get("clickid"));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransno() {
        return transno;
    }

    public void setTransno(String transno) {
        this.transno = transno;
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    public String getClickid() {
        return clickid;
    }

    public void setClickid(String clickid) {
        this.clickid = clickid;
    }
}
