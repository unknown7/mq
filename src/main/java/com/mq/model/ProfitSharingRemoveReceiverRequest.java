package com.mq.model;

import java.io.Serializable;

public class ProfitSharingRemoveReceiverRequest {

	/**
	 * 微信支付分配的商户号
	 */
	private String mchId;

	/**
	 * 微信分配的公众账号ID
	 */
	private String appId;

	/**
	 * 随机字符串，不长于32位。推荐随机数生成算法
	 */
	private String nonceStr;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 签名类型，目前只支持HMAC-SHA256
	 */
	private String signType;

	/**
	 * 分账接收方对象，json格式
	 */
	private Receiver receiver;

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public static class Receiver implements Serializable {

		/**
		 * MERCHANT_ID：商户号（mch_id或者sub_mch_id）
		 * PERSONAL_OPENID：个人openid
		 */
		private String type;

		/**
		 * 类型是MERCHANT_ID时，是商户号（mch_id或者sub_mch_id）
		 * 类型是PERSONAL_OPENID时，是个人openid
		 */
		private String account;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}
	}
}
