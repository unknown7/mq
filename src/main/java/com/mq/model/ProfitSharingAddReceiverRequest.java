package com.mq.model;

import java.io.Serializable;

public class ProfitSharingAddReceiverRequest {

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

		/**
		 * 分账接收方类型是MERCHANT_ID时，是商户全称（必传），当商户是小微商户或个体户时，是开户人姓名
		 * 分账接收方类型是PERSONAL_OPENID时，是个人姓名（选传，传则校验）
		 */
		private String name;

		/**
		 * 子商户与接收方的关系。
		 * 本字段值为枚举：
		 * SERVICE_PROVIDER：服务商
		 * STORE：门店
		 * STAFF：员工
		 * STORE_OWNER：店主
		 * PARTNER：合作伙伴
		 * HEADQUARTER：总部
		 * BRAND：品牌方
		 * DISTRIBUTOR：分销商
		 * USER：用户
		 * SUPPLIER：供应商
		 * CUSTOM：自定义
		 */
		private String relation_type;

		/**
		 * 子商户与接收方具体的关系，本字段最多10个字。
		 * 当字段relation_type的值为CUSTOM时，本字段必填
		 * 当字段relation_type的值不为CUSTOM时，本字段无需填写
		 */
		private String custom_relation;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRelation_type() {
			return relation_type;
		}

		public void setRelation_type(String relation_type) {
			this.relation_type = relation_type;
		}

		public String getCustom_relation() {
			return custom_relation;
		}

		public void setCustom_relation(String custom_relation) {
			this.custom_relation = custom_relation;
		}
	}
}
