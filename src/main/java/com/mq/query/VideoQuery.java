package com.mq.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class VideoQuery extends DefaultQuery {
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTimeEnd;

    private Integer delFlag;

    private Long classification;

    private BigDecimal profitShare;

    private Integer accessed;

    private String status;

    private Integer freeWatchTime;

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

    public BigDecimal getProfitShare() {
        return profitShare;
    }

    public void setProfitShare(BigDecimal profitShare) {
        this.profitShare = profitShare;
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

    public Integer getFreeWatchTime() {
        return freeWatchTime;
    }

    public void setFreeWatchTime(Integer freeWatchTime) {
        this.freeWatchTime = freeWatchTime;
    }
}
