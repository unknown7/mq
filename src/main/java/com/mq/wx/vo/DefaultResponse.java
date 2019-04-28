package com.mq.wx.vo;

public class DefaultResponse {
    private boolean success;
    private String data;
    private String msg;

    public static DefaultResponse create(boolean success, String data) {
        return create(success, data, null);
    }

    public static DefaultResponse create(boolean success, String data, String msg) {
        DefaultResponse response = new DefaultResponse();
        response.setSuccess(success);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    public static DefaultResponse success(String data) {
        return success(data, null);
    }

    public static DefaultResponse success(String data, String msg) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
