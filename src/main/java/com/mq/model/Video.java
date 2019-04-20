package com.mq.model;

import java.math.BigDecimal;
import java.util.Date;

public class Video {
    private Long id;

    private String title;

    private String subtitle;

    private BigDecimal price;

    private String coverRealName;

    private String descriptionRealName;

    private String videoRealName;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private Long classification;

    private BigDecimal shareCommission;

    private String status;

    private String coverName;

    private String descriptionName;

    private String videoName;

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
        this.title = title == null ? null : title.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCoverRealName() {
        return coverRealName;
    }

    public void setCoverRealName(String coverRealName) {
        this.coverRealName = coverRealName == null ? null : coverRealName.trim();
    }

    public String getDescriptionRealName() {
        return descriptionRealName;
    }

    public void setDescriptionRealName(String descriptionRealName) {
        this.descriptionRealName = descriptionRealName == null ? null : descriptionRealName.trim();
    }

    public String getVideoRealName() {
        return videoRealName;
    }

    public void setVideoRealName(String videoRealName) {
        this.videoRealName = videoRealName == null ? null : videoRealName.trim();
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

    public BigDecimal getShareCommission() {
        return shareCommission;
    }

    public void setShareCommission(BigDecimal shareCommission) {
        this.shareCommission = shareCommission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName == null ? null : coverName.trim();
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName == null ? null : descriptionName.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }
}