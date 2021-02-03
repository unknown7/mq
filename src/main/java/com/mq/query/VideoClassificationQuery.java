package com.mq.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class VideoClassificationQuery extends DefaultQuery {
    private Long id;

    private String vName;

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

    private Integer sort;

    private String desc;

    private BigDecimal defaultProfitShare;

    private BigDecimal defaultProfitSale;

    private Integer defaultFreeWatchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getDefaultProfitShare() {
        return defaultProfitShare;
    }

    public void setDefaultProfitShare(BigDecimal defaultProfitShare) {
        this.defaultProfitShare = defaultProfitShare;
    }

	public BigDecimal getDefaultProfitSale() {
		return defaultProfitSale;
	}

	public void setDefaultProfitSale(BigDecimal defaultProfitSale) {
		this.defaultProfitSale = defaultProfitSale;
	}

	public Integer getDefaultFreeWatchTime() {
        return defaultFreeWatchTime;
    }

    public void setDefaultFreeWatchTime(Integer defaultFreeWatchTime) {
        this.defaultFreeWatchTime = defaultFreeWatchTime;
    }
}
