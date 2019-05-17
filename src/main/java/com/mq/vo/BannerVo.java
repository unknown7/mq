package com.mq.vo;

import java.util.Date;

public class BannerVo {
    private Long id;

    private String imageName;

    private Integer sort;

    private Long jumpTo;
    private String jumpToName;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

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

    public String getJumpToName() {
        return jumpToName;
    }

    public void setJumpToName(String jumpToName) {
        this.jumpToName = jumpToName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
