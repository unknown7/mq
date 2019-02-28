package com.mq.model;

import java.util.Date;

public class VideoClassification {
    private Long id;

    private String vName;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private Integer sort;

    private String desc;

    private Double defaultShareCommission;

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

    public Double getDefaultShareCommission() {
        return defaultShareCommission;
    }

    public void setDefaultShareCommission(Double defaultShareCommission) {
        this.defaultShareCommission = defaultShareCommission;
    }
}