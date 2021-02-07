package com.mq.base;

public enum RspCode {

	ORDER_STATUS_ERROR("00000001", "订单状态异常，请检查"),
	INVITATION_STATUS_ERROR("00000002", "邀请记录状态异常，请检查"),
	REWARDS_STATUS_ERROR("00000003", "奖励记录状态异常，请检查"),
	PAYMENT_RESULT_ERROR("00000004", "支付结果异常，请检查"),
	PROFIT_SHARING_ERROR("00000005", "请求微信支付分账异常"),
	NO_CORRESPONDENT_ENUM_KEY("00000006", "未找到对应枚举"),
	;

	private String code;

	private String msg;

	RspCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
