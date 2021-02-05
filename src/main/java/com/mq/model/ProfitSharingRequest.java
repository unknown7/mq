package com.mq.model;

import java.io.Serializable;
import java.util.List;

public class ProfitSharingRequest {

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
	 * 微信支付订单号
	 */
	private String transactionId;

	/**
	 * 商户系统内部的分账单号，在商户系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），同一分账单号多次请求等同一次。只能是数字、大小写字母_-|*@
	 */
	private String outOrderNo;

	/**
	 * 分账接收方列表，不超过50个json对象，不能设置分账方作为分账接受方
	 */
	private List<Receiver> receivers;

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

	/**
	 * 分账金额，单位为分，只能为整数，不能超过原订单支付金额及最大分账比例金额
	 */
	private Integer amount;

	/**
	 * 分账的原因描述，分账账单中需要体现
	 */
	private String description;

	/**
	 * 可选项，在接收方类型为个人的时可选填，若有值，会检查与 name 是否实名匹配，不匹配会拒绝分账请求
	 * 1、分账接收方类型是PERSONAL_OPENID时，是个人姓名（选传，传则校验）
	 */
	private String name;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public List<Receiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<Receiver> receivers) {
		this.receivers = receivers;
	}

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

		/**
		 * 分账金额，单位为分，只能为整数，不能超过原订单支付金额及最大分账比例金额
		 */
		private Integer amount;

		/**
		 * 分账的原因描述，分账账单中需要体现
		 */
		private String description;

		/**
		 * 可选项，在接收方类型为个人的时可选填，若有值，会检查与 name 是否实名匹配，不匹配会拒绝分账请求
		 * 1、分账接收方类型是PERSONAL_OPENID时，是个人姓名（选传，传则校验）
		 */
		private String name;

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

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
