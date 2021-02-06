package com.mq.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProfitSharing {
    private Long id;

    private String profitSharingNo;

    private String orderNo;

    private BigDecimal orderAmount;

    private BigDecimal sharingAmount;

    private String transactionId;

    private String orderId;

    private Date createdTime;

    private Date modifiedTime;

    private Boolean delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfitSharingNo() {
        return profitSharingNo;
    }

    public void setProfitSharingNo(String profitSharingNo) {
        this.profitSharingNo = profitSharingNo == null ? null : profitSharingNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getSharingAmount() {
        return sharingAmount;
    }

    public void setSharingAmount(BigDecimal sharingAmount) {
        this.sharingAmount = sharingAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}