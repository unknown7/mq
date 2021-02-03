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

    private Date createdTime;

    private Date modifiedTime;

    private Integer delFlag;

    private Long classification;
    private String classificationName;

    private BigDecimal profitShare;

    private BigDecimal profitSale;

    private Integer accessed;

    private String status;

    private boolean isPurchased;

    private Integer freeWatchTime;

    private Long userId;

    private String shareCard;

    private boolean isWhiteUser;

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

    public BigDecimal getProfitShare() {
        return profitShare;
    }

    public void setProfitShare(BigDecimal profitShare) {
        this.profitShare = profitShare;
    }

	public BigDecimal getProfitSale() {
		return profitSale;
	}

	public void setProfitSale(BigDecimal profitSale) {
		this.profitSale = profitSale;
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

    public Integer getFreeWatchTime() {
        return freeWatchTime;
    }

    public void setFreeWatchTime(Integer freeWatchTime) {
        this.freeWatchTime = freeWatchTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getShareCard() {
        return shareCard;
    }

    public void setShareCard(String shareCard) {
        this.shareCard = shareCard;
    }

    public boolean getIsWhiteUser() {
        return isWhiteUser;
    }

    public void setIsWhiteUser(boolean isWhiteUser) {
        this.isWhiteUser = isWhiteUser;
    }
}
