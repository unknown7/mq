package com.mq.wx.vo.auth;

import com.mq.wx.vo.DefaultRequest;

public class AuthRequest extends DefaultRequest {
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

    private String js_code;

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

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }
}
