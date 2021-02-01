package com.mq.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserQuery extends DefaultQuery {
    private Long id;

    private String country;

    private Integer gender;

    private String province;

    private String city;

    private String avatarUrl;

    private String openId;

    private String nickName;

    private String language;

    private String sessionKey;

    private String skey;

    private Long referrer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedTimeBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedTimeEnd;

    private Boolean delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public Long getReferrer() {
        return referrer;
    }

    public void setReferrer(Long referrer) {
        this.referrer = referrer;
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

	public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}
