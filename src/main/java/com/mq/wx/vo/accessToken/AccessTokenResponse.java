package com.mq.wx.vo.accessToken;

public class AccessTokenResponse {
    /**
     * access_token	string	获取到的凭证
     * expires_in	number	凭证有效时间，单位：秒。目前是7200秒之内的值。
     * errcode	number	错误码
     * errmsg	string	错误信息
     *
     * errcode 的合法值
     * -1	    系统繁忙，此时请开发者稍候再试
     * 0	    请求成功
     * 40001	AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性
     * 40002	请确保 grant_type 字段值为 client_credential
     * 40013	不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写
     */
    private String access_token;
    private Integer expires_in;
    private Integer errcode;
    private String errmsg;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
