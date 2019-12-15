package com.ref.bean;

import java.io.Serializable;
import java.util.Map;

public class ResponseRedirectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String msisdn;
    private String status;
    private String transno;

    public ResponseRedirectDTO(Map<String, String> requestParams) {
        this.setMsisdn(requestParams.get("msisdn"));
        this.setStatus(requestParams.get("status"));
        this.setTransno(requestParams.get("transno"));
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransno() {
        return transno;
    }

    public void setTransno(String transno) {
        this.transno = transno;
    }
}
