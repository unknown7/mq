package com.mq.wx.vo;

public class DefaultResponse {
    private boolean success;
    private Object data;
    private String msg;

    public static DefaultResponse create(boolean success, Object data) {
        return create(success, data, null);
    }

    public static DefaultResponse create(boolean success, Object data, String msg) {
        DefaultResponse response = new DefaultResponse();
        response.setSuccess(success);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    public static DefaultResponse success(Object data) {
        return success(data, null);
    }

    public static DefaultResponse success(Object data, String msg) {
        DefaultResponse response = new DefaultResponse();
        response.setSuccess(true);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    public static DefaultResponse fail() {
        return fail(null);
    }

    public static DefaultResponse fail(String msg) {
        DefaultResponse response = new DefaultResponse();
        response.setSuccess(false);
        response.setMsg(msg);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
