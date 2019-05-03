package com.mq.wx.base;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.base.Http;
import com.mq.base.RedisObjectHolder;
import com.mq.model.UnifiedOrderRequest;
import com.mq.model.UnifiedOrderResponse;
import com.mq.util.MD5;
import com.mq.util.OrderNoGenerator;
import com.mq.wx.vo.accessToken.AccessTokenResponse;
import com.mq.wx.vo.auth.AuthResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 微信API调用
 */

@Component
public class WxAPI {
    @javax.annotation.Resource
    private Http http;
    @javax.annotation.Resource
    private RedisObjectHolder redisObjectHolder;

    /**
     * 获取access_token，如果缓存中存在，直接返回，如果不存在，调用微信接口，获取token并存入缓存
     *
     * @return access_token
     */
    public String getAccessToken() {
        String accessToken = redisObjectHolder.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            String domain = "https://api.weixin.qq.com/cgi-bin/token";
            Map<String, Object> params = Maps.newHashMap();
            params.put("appid", GlobalConstants.APP_ID);
            params.put("secret", GlobalConstants.APP_SECRET);
            params.put("grant_type", GlobalConstants.GrantType.CLIENT_CREDENTIAL.getKey());
            ResponseEntity<String> responseEntity = http.getForEntity(domain, params, String.class);
            String result = responseEntity.getBody();
            if (!StringUtils.isEmpty(result)) {
                AccessTokenResponse response = JSONObject.parseObject(result, AccessTokenResponse.class);
                if (!StringUtils.isEmpty(response.getAccess_token())) {
                    accessToken = response.getAccess_token();
                    redisObjectHolder.setAccessToken(response.getAccess_token(), response.getExpires_in());
                }
            }
        }
        return accessToken;
    }

    /**
     * 生成小程序码
     *
     * @param page
     * @param scene
     * @return
     */
    public String getUnlimited(String page, Map<String, Object> scene) {
        String accessToken = getAccessToken();
        StringBuffer domain = new StringBuffer("https://api.weixin.qq.com/wxa/getwxacodeunlimit");
        domain.append("?access_token=").append(accessToken);
        Map<String, Object> params = Maps.newHashMap();
//        params.put("page", page);
        params.put("scene", http.map2param(scene));
        params.put("is_hyaline", true);
        ResponseEntity<Resource> responseEntity = http.postForEntity(domain.toString(), params, Resource.class, MediaType.APPLICATION_JSON_UTF8);
        MediaType contentType = responseEntity.getHeaders().getContentType();
        System.err.println(contentType);
        String miniProgramCode = null;
        /**
         * 成功的contentType为image/jpeg
         * 失败的contentType为application/json
         */
        if (MediaType.IMAGE_JPEG.equals(contentType)) {
            try {
                InputStream is = responseEntity.getBody().getInputStream();
                miniProgramCode = UUID.randomUUID().toString().concat(".jpg");
                FileOutputStream fos = new FileOutputStream(GlobalConstants.IMAGE_PATH.concat(miniProgramCode));
                byte[] b = new byte[1024];
                int length;
                while ((length = is.read(b)) != -1) {
                    fos.write(b, 0, length);
                }
                is.close();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return miniProgramCode;
    }

    /**
     * 获取用户认证结果
     *
     * @param code
     * @return
     */
    public AuthResponse jscode2session(String code) {
        String domain = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> params = Maps.newHashMap();
        params.put("appid", GlobalConstants.APP_ID);
        params.put("secret", GlobalConstants.APP_SECRET);
        params.put("grant_type", GlobalConstants.GrantType.AUTHORIZATION_CODE.getKey());
        params.put("js_code", code);
        ResponseEntity<String> responseEntity = http.getForEntity(domain, params, String.class);
        AuthResponse authResponse = JSONObject.parseObject(responseEntity.getBody(), AuthResponse.class);
        return authResponse;
    }

    /**
     * 统一下单
     *
     * @param request
     * @throws Exception
     */
    public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest request) throws Exception {
        String domain = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Document doc = DocumentHelper.createDocument();
        Element xml = doc.addElement("xml");
        /**
         * appid
         */
        Element appid = xml.addElement("appid");
        appid.setText(request.getAppid());
        /**
         * mch_id
         */
        Element mch_id = xml.addElement("mch_id");
        mch_id.setText(request.getMchId());
        /**
         * nonce_str
         */
        Element nonce_str = xml.addElement("nonce_str");
        nonce_str.setText(request.getNonceStr());
        /**
         * body
         */
        Element body = xml.addElement("body");
        body.setText(request.getBody());
        /**
         * out_trade_no
         */
        Element out_trade_no = xml.addElement("out_trade_no");
        out_trade_no.setText(request.getOutTradeNo());
        /**
         * total_fee
         */
        Element total_fee = xml.addElement("total_fee");
        total_fee.setText(request.getTotalFee().toString());
        /**
         * spbill_create_ip
         */
        Element spbill_create_ip = xml.addElement("spbill_create_ip");
        spbill_create_ip.setText(request.getSpbillCreateIp());
        /**
         * notify_url
         */
        Element notify_url = xml.addElement("notify_url");
        notify_url.setText(request.getNotifyUrl());
        /**
         * trade_type
         */
        Element trade_type = xml.addElement("trade_type");
        trade_type.setText(request.getTradeType());
        /**
         * openid
         */
        Element openid = xml.addElement("openid");
        openid.setText(request.getOpenid());
        /**
         * sign
         */
        Element sign = xml.addElement("sign");
        Map<String, Object> signData = Maps.newLinkedHashMap();
        signData.put("appid", appid.getStringValue());
        signData.put("body", body.getStringValue());
        signData.put("mch_id", mch_id.getStringValue());
        signData.put("nonce_str", nonce_str.getStringValue());
        signData.put("notify_url", notify_url.getStringValue());
        signData.put("openid", openid.getStringValue());
        signData.put("out_trade_no", out_trade_no.getStringValue());
        signData.put("spbill_create_ip", spbill_create_ip.getStringValue());
        signData.put("total_fee", total_fee.getStringValue());
        signData.put("trade_type", trade_type.getStringValue());
        signData.put("key", GlobalConstants.API_KEY);
        String signStr = MD5.generate(http.map2param(signData)).toUpperCase();
        sign.setText(signStr);
        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        output.write(doc);
        writer.close();
        output.close();
        ResponseEntity<String> responseEntity = http.postForEntity(domain, writer.toString(), String.class, MediaType.APPLICATION_XML);
        String response = responseEntity.getBody();
        doc = DocumentHelper.parseText(response);
        Element root = doc.getRootElement();
        Iterator<Element> iter = root.elementIterator("head");
        while (iter.hasNext()) {
            Element element = iter.next();
            System.err.println(element.getStringValue());
        }
    }
}
