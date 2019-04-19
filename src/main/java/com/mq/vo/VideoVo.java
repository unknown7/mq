package com.mq.vo;

import java.math.BigDecimal;
import java.util.Date;

public class VideoVo {
    private Long id;

    private String title;

    private String subtitle;

    private BigDecimal price;

    private Integer watched;

    private Integer purchased;

    private String coverRealName;
    private String coverName;

    private String descriptionRealName;
    private String descriptionName;

    private String videoRealName;
    private String videoName;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private Long classification;
    private String classificationName;

    private BigDecimal shareCommission;

    private Integer accessed;

    private String status;

    private boolean isPurchased;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public Integer getPurchased() {
        return purchased;
    }

    public void setPurchased(Integer purchased) {
        this.purchased = purchased;
    }

    public String getCoverRealName() {
        return coverRealName;
    }

    public void setCoverRealName(String coverRealName) {
        this.coverRealName = coverRealName;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public String getDescriptionRealName() {
        return descriptionRealName;
    }

    public void setDescriptionRealName(String descriptionRealName) {
        this.descriptionRealName = descriptionRealName;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public String getVideoRealName() {
        return videoRealName;
    }

    public void setVideoRealName(String videoRealName) {
        this.videoRealName = videoRealName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
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

    public Long getClassification() {
        return classification;
    }

    public void setClassification(Long classification) {
        this.classification = classification;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public BigDecimal getShareCommission() {
        return shareCommission;
    }

    public void setShareCommission(BigDecimal shareCommission) {
        this.shareCommission = shareCommission;
    }

    public Integer getAccessed() {
        return accessed;
    }

    public void setAccessed(Integer accessed) {
        this.accessed = accessed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }
}
