package com.mq.vo;

import java.util.Date;

public class InvitationRecordVo {

    private Long id;

    private Long inviteeId;

    private String inviteeSkey;

    private Long inviterId;

    private Long shareCardId;

    private Long goodsId;

    private String goodsType;

    private String status;

    private Date createdTime;

    private Date modifiedTime;

    private Boolean delFlag;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 被邀请人昵称
     */
    private String inviteeNickName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(Long inviteeId) {
        this.inviteeId = inviteeId;
    }

    public String getInviteeSkey() {
        return inviteeSkey;
    }

    public void setInviteeSkey(String inviteeSkey) {
        this.inviteeSkey = inviteeSkey;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Long getShareCardId() {
        return shareCardId;
    }

    public void setShareCardId(Long shareCardId) {
        this.shareCardId = shareCardId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getInviteeNickName() {
        return inviteeNickName;
    }

    public void setInviteeNickName(String inviteeNickName) {
        this.inviteeNickName = inviteeNickName;
    }
}
