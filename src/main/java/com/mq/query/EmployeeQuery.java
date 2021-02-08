package com.mq.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeQuery extends DefaultQuery {

    private Long id;

    private String employeeName;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthBegin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthEnd;

    private Integer age;
    private Integer ageBegin;
    private Integer ageEnd;

    private String avatarRealName;
    private String avatarName;

    private Date createdTime;
    private Date createdTimeBegin;
    private Date createdTimeEnd;

    private Date modifiedTime;
    private Date modifiedTimeBegin;
    private Date modifiedTimeEnd;

    private Integer delFlag;

    private String password;

    private String username;

    private String email;

    private String mobile;

    private String openId;

    private BigDecimal profitRate;

    private Integer start;

    private Integer length;

    private Integer page;

    private String searchName;

    private Boolean paymentShareReceiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getBirthBegin() {
        return birthBegin;
    }

    public void setBirthBegin(Date birthBegin) {
        this.birthBegin = birthBegin;
    }

    public Date getBirthEnd() {
        return birthEnd;
    }

    public void setBirthEnd(Date birthEnd) {
        this.birthEnd = birthEnd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAgeBegin() {
        return ageBegin;
    }

    public void setAgeBegin(Integer ageBegin) {
        this.ageBegin = ageBegin;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getAvatarRealName() {
        return avatarRealName;
    }

    public void setAvatarRealName(String avatarRealName) {
        this.avatarRealName = avatarRealName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

	public Boolean getPaymentShareReceiver() {
		return paymentShareReceiver;
	}

	public void setPaymentShareReceiver(Boolean paymentShareReceiver) {
		this.paymentShareReceiver = paymentShareReceiver;
	}
}
