package com.mq.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ShareCardQuery {
    private Long id;

    private Long userId;

    private String skey;

    private String shareCardRealName;

    private Long goodsId;

    private String goodsType;

    private BigDecimal goodsPrice;

    private BigDecimal profitShare;

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

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getShareCardRealName() {
        return shareCardRealName;
    }

    public void setShareCardRealName(String shareCardRealName) {
        this.shareCardRealName = shareCardRealName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getProfitShare() {
        return profitShare;
    }

    public void setProfitShare(BigDecimal profitShare) {
        this.profitShare = profitShare;
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
}
