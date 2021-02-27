package com.mq.model;

import java.util.Date;

public class Banner {
    private Long id;

    private String imageName;

    private Integer sort;

    private Long jumpTo;

    private Boolean delFlag;

    private Date createdTime;

    private Date modifiedTime;

    private String desc;

    private String imageRealName;

    private String bName;

    private String collection;

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
        this.imageName = imageName == null ? null : imageName.trim();
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

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getImageRealName() {
        return imageRealName;
    }

    public void setImageRealName(String imageRealName) {
        this.imageRealName = imageRealName == null ? null : imageRealName.trim();
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName == null ? null : bName.trim();
    }

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
}