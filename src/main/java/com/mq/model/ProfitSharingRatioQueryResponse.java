package com.mq.model;

import java.math.BigDecimal;

public class ProfitSharingRatioQueryResponse {

	private String returnCode;

	/**
	 * 最大分账比例（小数）
	 */
	private BigDecimal maxRatio;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public BigDecimal getMaxRatio() {
		return maxRatio;
	}

	public void setMaxRatio(BigDecimal maxRatio) {
		this.maxRatio = maxRatio;
	}

	private static final String SUCCESS = "SUCCESS";

	public Boolean success() {
		return SUCCESS.equals(this.returnCode);
	}
}
