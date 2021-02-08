package com.mq.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ProfitSharingVo {

    private Long id;

    private String profitSharingNo;

    private String orderNo;

    private BigDecimal orderAmount;

    private BigDecimal sharingAmount;

    private String transactionId;

    private String orderId;

    private String employeeId;

    private Date createdTime;

    private Date modifiedTime;

    private Boolean delFlag;

    private String employeeName;

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
        this.profitSharingNo = profitSharingNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
