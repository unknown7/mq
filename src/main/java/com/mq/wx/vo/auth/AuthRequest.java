package com.mq.wx.vo.auth;

import com.mq.wx.vo.DefaultRequest;

public class AuthRequest extends DefaultRequest {
    /**
     * rawData          // 用户非敏感信息
     * signature        // 签名
     * encryptedData    // 用户敏感信息
     * iv               // 解密算法的向量
     *
     * appid            // appid
     * secret           // appsecret
     * grant_type       // 微信默认authorization_code
     */
    private String skey;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
    private String scene;
    private Long shareCardId;

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
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

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public Long getShareCardId() {
        return shareCardId;
    }

    public void setShareCardId(Long shareCardId) {
        this.shareCardId = shareCardId;
    }
}
