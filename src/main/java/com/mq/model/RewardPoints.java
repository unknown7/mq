package com.mq.model;

import java.math.BigDecimal;
import java.util.Date;

public class RewardPoints {
    private Long id;

    private Long userId;

    private BigDecimal points;

    private String rewardType;

    private Long rewardId;

    private Long profitFrom;

    private String pointsStatus;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType == null ? null : rewardType.trim();
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public Long getProfitFrom() {
        return profitFrom;
    }

    public void setProfitFrom(Long profitFrom) {
        this.profitFrom = profitFrom;
    }

    public String getPointsStatus() {
        return pointsStatus;
    }

    public void setPointsStatus(String pointsStatus) {
        this.pointsStatus = pointsStatus == null ? null : pointsStatus.trim();
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