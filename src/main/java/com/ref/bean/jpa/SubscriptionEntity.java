package com.ref.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ref.UTCDateTimeConverter;

@Entity
@Table(name = "subscription")
public class SubscriptionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created_at")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdAt;

    @Column(name = "status")
    private String status;

    @Column(name = "re_sub")
    private Boolean reSub;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "internal_txn_no")
    private Integer internalTxnNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getReSub() {
        return reSub;
    }

    public void setReSub(Boolean reSub) {
        this.reSub = reSub;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getInternalTxnNo() {
        return internalTxnNo;
    }

    public void setInternalTxnNo(Integer internalTxnNo) {
        this.internalTxnNo = internalTxnNo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
