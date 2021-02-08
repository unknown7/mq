package com.mq.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ProfitSharingQuery extends DefaultQuery {

    private Long id;

    private String profitSharingNo;

    private String orderNo;

    private BigDecimal orderAmount;

    private BigDecimal sharingAmount;

    private String transactionId;

    private String orderId;

    private Long employeeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedTimeEnd;

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTimeBegin() {
        return createdTimeBegin;
    }

    public void setCreatedTimeBegin(Date createdTimeBegin) {
        this.createdTimeBegin = createdTimeBegin;
    }

    public Date getCreatedTimeEnd() {
        return createdTimeEnd;
    }

    public void setCreatedTimeEnd(Date createdTimeEnd) {
        this.createdTimeEnd = createdTimeEnd;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getModifiedTimeBegin() {
        return modifiedTimeBegin;
    }

    public void setModifiedTimeBegin(Date modifiedTimeBegin) {
        this.modifiedTimeBegin = modifiedTimeBegin;
    }

    public Date getModifiedTimeEnd() {
        return modifiedTimeEnd;
    }

    public void setModifiedTimeEnd(Date modifiedTimeEnd) {
        this.modifiedTimeEnd = modifiedTimeEnd;
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
