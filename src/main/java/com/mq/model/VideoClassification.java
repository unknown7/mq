package com.mq.model;

import java.math.BigDecimal;
import java.util.Date;

public class VideoClassification {
    private Long id;

    private String vName;

    private Date createdTime;

    private Date modifiedTime;

    private Boolean delFlag;

    private Integer sort;

    private String desc;

    private BigDecimal defaultProfitShare;

    private Integer defaultFreeWatchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName == null ? null : vName.trim();
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public BigDecimal getDefaultProfitShare() {
        return defaultProfitShare;
    }

    public void setDefaultProfitShare(BigDecimal defaultProfitShare) {
        this.defaultProfitShare = defaultProfitShare;
    }

    public Integer getDefaultFreeWatchTime() {
        return defaultFreeWatchTime;
    }

    public void setDefaultFreeWatchTime(Integer defaultFreeWatchTime) {
        this.defaultFreeWatchTime = defaultFreeWatchTime;
    }
}