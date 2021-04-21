package com.mq.ex;

public class BaseBusinessException extends RuntimeException {

	private String msg;

	public BaseBusinessException() {
	}

	public BaseBusinessException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
