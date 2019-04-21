package com.mq.model;

import java.util.Date;

public class Statistics {
    private Long id;

    private String statisticsType;

    private Long businessId;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType == null ? null : statisticsType.trim();
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}