package com.mq.model;

public class ProfitSharingResponse {

	/**
	 * SUCCESS/FAIL 此字段是通信标识，非交易标识
	 */
	private String returnCode;

	/**
	 * 返回信息，如非空，为错误原因
	 */
	private String returnMsg;

	/**
	 * SUCCESS：添加分账接收方成功
	 * FAIL ：提交业务失败
	 */
	private String resultCode;

	/**
	 * 列表详见错误码列表
	 */
	private String errCode;

	/**
	 * 结果信息描述
	 */
	private String errCodeDes;

	/**
	 * 调用接口时提供的商户号
	 */
	private String mchId;

	/**
	 * 调用接口提供的公众账号ID
	 */
	private String appid;

	/**
	 * 微信返回的随机字符串
	 */
	private String nonceStr;

	/**
	 * 微信返回的签名
	 */
	private String sign;

	/**
	 * 微信支付订单号
	 */
	private String transactionId;

	/**
	 * 调用接口提供的商户系统内部的分账单号
	 */
	private String outOrderNo;

	/**
	 * 微信分账单号，微信系统返回的唯一标识
	 */
	private String orderId;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	private static final String SUCCESS = "SUCCESS";

	public Boolean success() {
		return SUCCESS.equals(this.returnCode) && SUCCESS.equals(this.resultCode);
	}
}
