package com.mq.wx.vo;

public class LoginRequest {
    /**
     * code             // 临时登录凭证
     * rawData          // 用户非敏感信息
     * signature        // 签名
     * encryptedData    // 用户敏感信息
     * iv               // 解密算法的向量
     *
     * appid            // appid
     * secret           // appsecret
     * js_code          // 临时登录凭证（对应code）
     * grant_type       // 微信默认authorization_code
     */
    private String code;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;

    private String appid;
    private String secret;
    private String js_code;
    private String grant_type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
