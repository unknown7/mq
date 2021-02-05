package com.mq.model;

public class ProfitSharingAddReceiverResponse {

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
	 * 分账接收方对象（不包含分账接收方全称），json格式
	 */
	private String receiver;

	/**
	 * 微信返回的随机字符串
	 */
	private String nonceStr;

	/**
	 * 微信返回的签名
	 */
	private String sign;

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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
}
