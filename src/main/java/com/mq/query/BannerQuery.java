package com.mq.query;

import java.util.Date;

public class BannerQuery extends DefaultQuery {
    private Long id;

    private String imageName;

    private Integer sort;

    private Long jumpTo;

    private Integer delFlag;

    private Date createdTime;
    private Date createdTimeBegin;
    private Date createdTimeEnd;

    private Date modifiedTime;
    private Date modifiedTimeBegin;
    private Date modifiedTimeEnd;

    private String desc;

    private String imageRealName;

    private String bName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getJumpTo() {
        return jumpTo;
    }

    public void setJumpTo(Long jumpTo) {
        this.jumpTo = jumpTo;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

	public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageRealName() {
        return imageRealName;
    }

    public void setImageRealName(String imageRealName) {
        this.imageRealName = imageRealName;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }
}
