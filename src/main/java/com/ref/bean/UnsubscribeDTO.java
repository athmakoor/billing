package com.ref.bean;

import java.io.Serializable;
import java.util.Map;

public class UnsubscribeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String msisdn;
    private String date;
    private String transno;
    private String packageid;
    private String cidentifier;

    public UnsubscribeDTO(Map<String, String> requestParams) {
        this.setKey(requestParams.get("key"));
        this.setDate(requestParams.get("date"));
        this.setTransno(requestParams.get("transno"));
        this.setPackageid(requestParams.get("packageid"));
        this.setMsisdn(requestParams.get("MSISDN"));
        this.setCidentifier(requestParams.get("cidentifier"));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getCidentifier() {
        return cidentifier;
    }

    public void setCidentifier(String cidentifier) {
        this.cidentifier = cidentifier;
    }
}
