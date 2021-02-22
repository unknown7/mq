package com.mq.wx.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import com.mq.base.Http;
import com.mq.base.RedisObjectHolder;
import com.mq.model.*;
import com.mq.util.MD5;
import com.mq.util.MapUtil;
import com.mq.wx.vo.accessToken.AccessTokenResponse;
import com.mq.wx.vo.auth.AuthResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

/**
 * 微信API调用
 */

@Component
public class WxAPI {
    protected static final Logger logger = LoggerFactory.getLogger(WxAPI.class);
    @Resource
    private Http http;
	@Resource
    private RedisObjectHolder redisObjectHolder;
	@Resource
    private GlobalConstants globalConstants;

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
            params.put("appid", globalConstants.getAppId());
            params.put("secret", globalConstants.getAppSecret());
            params.put("grant_type", Enums.GrantType.CLIENT_CREDENTIAL.getKey());
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
     * @param shareCardId
     * @return
     */
    public String getUnlimited(String page, Long shareCardId) {
        String accessToken = getAccessToken();
        StringBuffer domain = new StringBuffer("https://api.weixin.qq.com/wxa/getwxacodeunlimit");
        domain.append("?access_token=").append(accessToken);
        Map<String, Object> params = Maps.newHashMap();
        params.put("page", page);
        params.put("scene", shareCardId);
        params.put("is_hyaline", true);
        ResponseEntity<org.springframework.core.io.Resource> responseEntity = http.postForEntity(domain.toString(), params, org.springframework.core.io.Resource.class, MediaType.APPLICATION_JSON_UTF8);
        MediaType contentType = responseEntity.getHeaders().getContentType();
        System.err.println(contentType);
        logger.info("contentType:" + contentType);
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
        params.put("appid", globalConstants.getAppId());
        params.put("secret", globalConstants.getAppSecret());
        params.put("grant_type", Enums.GrantType.AUTHORIZATION_CODE.getKey());
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
         * attach
         */
        if (!StringUtils.isEmpty(request.getAttach())) {
            Element attach = xml.addElement("attach");
            attach.setText(request.getAttach());
        }
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
		 * profit_sharing
		 */
		if (!StringUtils.isEmpty(request.getProfitSharing())) {
			Element profit_sharing = xml.addElement("profit_sharing");
			profit_sharing.setText(request.getProfitSharing());
		}
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
        if (!StringUtils.isEmpty(request.getAttach())) {
            signData.put("attach", request.getAttach());
        }
        signData.put("body", body.getStringValue());
        signData.put("mch_id", mch_id.getStringValue());
        signData.put("nonce_str", nonce_str.getStringValue());
        signData.put("notify_url", notify_url.getStringValue());
        signData.put("openid", openid.getStringValue());
        signData.put("out_trade_no", out_trade_no.getStringValue());
		if (!StringUtils.isEmpty(request.getProfitSharing())) {
			signData.put("profit_sharing", request.getProfitSharing());
		}
        signData.put("spbill_create_ip", spbill_create_ip.getStringValue());
        signData.put("total_fee", total_fee.getStringValue());
        signData.put("trade_type", trade_type.getStringValue());
        signData.put("key", globalConstants.getApiKey());
        String signStr = MD5.generate(MapUtil.map2str(signData)).toUpperCase();
        sign.setText(signStr);
        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter requestString = new StringWriter();
        XMLWriter output = new XMLWriter(requestString, format);
        output.write(doc);
        requestString.close();
        output.close();
        ResponseEntity<String> responseEntity = http.postForEntity(domain, requestString.toString(), String.class, MediaType.APPLICATION_XML);
        logger.info("用户：" + request.getOpenid() + "统一下单，request：" + requestString);
        String responseString = responseEntity.getBody();
        logger.info("用户：" + request.getOpenid() + "统一下单，response：" + responseString);
        doc = DocumentHelper.parseText(responseString);
        Element root = doc.getRootElement();
        Element r_return_code = root.element("return_code");
        Element r_return_msg = root.element("return_msg");
        Element r_appid = root.element("appid");
        Element r_mch_id = root.element("mch_id");
        Element r_nonce_str = root.element("nonce_str");
        Element r_sign = root.element("sign");
        Element r_result_code = root.element("result_code");
        Element r_prepay_id = root.element("prepay_id");
        Element r_trade_type = root.element("trade_type");
        Element r_err_code = root.element("err_code");
        Element r_err_code_des = root.element("err_code_des");
        UnifiedOrderResponse response = new UnifiedOrderResponse();
        response.setReturnCode(r_return_code.getStringValue());
        response.setReturnMsg(r_return_msg.getStringValue());
        response.setAppid(r_appid.getStringValue());
        response.setMchId(r_mch_id.getStringValue());
        response.setNonceStr(r_nonce_str.getStringValue());
        response.setResultCode(r_result_code.getStringValue());
        if (r_err_code == null) {
            response.setSign(r_sign.getStringValue());
            response.setPrepayId(r_prepay_id.getStringValue());
            response.setTradeType(r_trade_type.getStringValue());
        } else {
            response.setErrCode(r_err_code.getStringValue());
            response.setErrCodeDes(r_err_code_des.getStringValue());
        }
        return response;
    }

    public ProfitSharingAddReceiverResponse profitSharingAddReceiver(ProfitSharingAddReceiverRequest request)
			throws Exception {
		String domain = "https://api.mch.weixin.qq.com/pay/profitsharingaddreceiver";
		Document doc = DocumentHelper.createDocument();
		Element xml = doc.addElement("xml");
		/**
		 * appid
		 */
		Element appid = xml.addElement("appid");
		appid.setText(request.getAppId());
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
		 * receiver
		 */
		Element receiver = xml.addElement("receiver");
		receiver.setText(JSON.toJSONString(request.getReceiver()));

		/**
		 * sign
		 */
		Element sign = xml.addElement("sign");
		Map<String, Object> signData = Maps.newLinkedHashMap();
		signData.put("appid", appid.getStringValue());
		signData.put("mch_id", mch_id.getStringValue());
		signData.put("nonce_str", nonce_str.getStringValue());
		signData.put("receiver", receiver.getStringValue());
		String signStr = MD5.generate(MapUtil.map2str(signData)).toUpperCase();
		sign.setText(signStr);
		OutputFormat format = OutputFormat.createCompactFormat();
		StringWriter requestString = new StringWriter();
		XMLWriter output = new XMLWriter(requestString, format);
		output.write(doc);
		requestString.close();
		output.close();
		ResponseEntity<String> responseEntity = http.postForEntity(domain, requestString.toString(), String.class, MediaType.APPLICATION_XML);
		logger.info("请求添加分账人员，request：" + requestString);
		String responseString = responseEntity.getBody();
		logger.info("请求添加分账人员，response：" + responseString);
		doc = DocumentHelper.parseText(responseString);
		Element root = doc.getRootElement();
		Element r_return_code = root.element("return_code");
		Element r_return_msg = root.element("return_msg");
		Element r_appid = root.element("appid");
		Element r_mch_id = root.element("mch_id");
		Element r_nonce_str = root.element("nonce_str");
		Element r_sign = root.element("sign");
		Element r_result_code = root.element("result_code");
		Element r_receiver = root.element("receiver");
		Element r_err_code = root.element("err_code");
		Element r_err_code_des = root.element("err_code_des");
		ProfitSharingAddReceiverResponse response = new ProfitSharingAddReceiverResponse();
		response.setReturnCode(r_return_code.getStringValue());
		response.setReturnMsg(r_return_msg.getStringValue());
		response.setAppid(r_appid.getStringValue());
		response.setMchId(r_mch_id.getStringValue());
		response.setNonceStr(r_nonce_str.getStringValue());
		response.setResultCode(r_result_code.getStringValue());
		if (r_err_code == null) {
			response.setSign(r_sign.getStringValue());
			response.setReceiver(r_receiver.getStringValue());
		} else {
			response.setErrCode(r_err_code.getStringValue());
			response.setErrCodeDes(r_err_code_des.getStringValue());
		}
		return response;
	}

	public ProfitSharingRemoveReceiverResponse profitSharingRemoveReceiver(ProfitSharingRemoveReceiverRequest request)
			throws Exception {
		String domain = "https://api.mch.weixin.qq.com/pay/profitsharingremovereceiver";
		Document doc = DocumentHelper.createDocument();
		Element xml = doc.addElement("xml");
		/**
		 * appid
		 */
		Element appid = xml.addElement("appid");
		appid.setText(request.getAppId());
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
		 * receiver
		 */
		Element receiver = xml.addElement("receiver");
		receiver.setText(JSON.toJSONString(request.getReceiver()));

		/**
		 * sign
		 */
		Element sign = xml.addElement("sign");
		Map<String, Object> signData = Maps.newLinkedHashMap();
		signData.put("appid", appid.getStringValue());
		signData.put("mch_id", mch_id.getStringValue());
		signData.put("nonce_str", nonce_str.getStringValue());
		signData.put("receiver", receiver.getStringValue());
		String signStr = MD5.generate(MapUtil.map2str(signData)).toUpperCase();
		sign.setText(signStr);
		OutputFormat format = OutputFormat.createCompactFormat();
		StringWriter requestString = new StringWriter();
		XMLWriter output = new XMLWriter(requestString, format);
		output.write(doc);
		requestString.close();
		output.close();
		ResponseEntity<String> responseEntity = http.postForEntity(domain, requestString.toString(), String.class, MediaType.APPLICATION_XML);
		logger.info("请求删除分账人员，request：" + requestString);
		String responseString = responseEntity.getBody();
		logger.info("请求删除分账人员，response：" + responseString);
		doc = DocumentHelper.parseText(responseString);
		Element root = doc.getRootElement();
		Element r_return_code = root.element("return_code");
		Element r_return_msg = root.element("return_msg");
		Element r_appid = root.element("appid");
		Element r_mch_id = root.element("mch_id");
		Element r_nonce_str = root.element("nonce_str");
		Element r_sign = root.element("sign");
		Element r_result_code = root.element("result_code");
		Element r_receiver = root.element("receiver");
		Element r_err_code = root.element("err_code");
		Element r_err_code_des = root.element("err_code_des");
		ProfitSharingRemoveReceiverResponse response = new ProfitSharingRemoveReceiverResponse();
		response.setReturnCode(r_return_code.getStringValue());
		response.setReturnMsg(r_return_msg.getStringValue());
		response.setAppid(r_appid.getStringValue());
		response.setMchId(r_mch_id.getStringValue());
		response.setNonceStr(r_nonce_str.getStringValue());
		response.setResultCode(r_result_code.getStringValue());
		if (r_err_code == null) {
			response.setSign(r_sign.getStringValue());
			response.setReceiver(r_receiver.getStringValue());
		} else {
			response.setErrCode(r_err_code.getStringValue());
			response.setErrCodeDes(r_err_code_des.getStringValue());
		}
		return response;
	}

	public ProfitSharingResponse profitSharing(ProfitSharingRequest request) throws Exception {
		String domain = "https://api.mch.weixin.qq.com/secapi/pay/profitsharing";
		Document doc = DocumentHelper.createDocument();
		Element xml = doc.addElement("xml");
		/**
		 * appid
		 */
		Element appid = xml.addElement("appid");
		appid.setText(request.getAppId());
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
		 * out_order_no
		 */
		Element out_order_no = xml.addElement("out_order_no");
		out_order_no.setText(request.getOutOrderNo());
		/**
		 * receivers
		 */
		Element receivers = xml.addElement("receivers");
		receivers.setText(JSON.toJSONString(request.getReceivers()));
		/**
		 * transaction_id
		 */
		Element transaction_id = xml.addElement("transaction_id");
		transaction_id.setText(request.getTransactionId());

		/**
		 * sign
		 */
		Element sign = xml.addElement("sign");
		Map<String, Object> signData = Maps.newLinkedHashMap();
		signData.put("appid", appid.getStringValue());
		signData.put("mch_id", mch_id.getStringValue());
		signData.put("nonce_str", nonce_str.getStringValue());
		signData.put("out_order_no", out_order_no.getStringValue());
		signData.put("receivers", receivers.getStringValue());
		signData.put("transaction_id", transaction_id.getStringValue());
		signData.put("key", globalConstants.getApiKey());
		String signStr = MD5.generate(MapUtil.map2str(signData)).toUpperCase();
		sign.setText(signStr);
		OutputFormat format = OutputFormat.createCompactFormat();
		StringWriter requestString = new StringWriter();
		XMLWriter output = new XMLWriter(requestString, format);
		output.write(doc);
		requestString.close();
		output.close();
		ResponseEntity<String> responseEntity = http.postForEntity(domain, requestString.toString(), String.class, MediaType.APPLICATION_XML);
		logger.info("请求分账，request：" + requestString);
		String responseString = responseEntity.getBody();
		logger.info("请求分账，response：" + responseString);
		doc = DocumentHelper.parseText(responseString);
		Element root = doc.getRootElement();
		Element r_return_code = root.element("return_code");
		Element r_return_msg = root.element("return_msg");
		Element r_appid = root.element("appid");
		Element r_mch_id = root.element("mch_id");
		Element r_nonce_str = root.element("nonce_str");
		Element r_sign = root.element("sign");
		Element r_result_code = root.element("result_code");
		Element r_transaction_id = root.element("transaction_id");
		Element r_out_order_no = root.element("out_order_no");
		Element r_order_id = root.element("order_id");
		Element r_err_code = root.element("err_code");
		Element r_err_code_des = root.element("err_code_des");
		ProfitSharingResponse response = new ProfitSharingResponse();
		response.setReturnCode(r_return_code.getStringValue());
		response.setReturnMsg(r_return_msg.getStringValue());
		response.setAppid(r_appid.getStringValue());
		response.setMchId(r_mch_id.getStringValue());
		response.setNonceStr(r_nonce_str.getStringValue());
		response.setResultCode(r_result_code.getStringValue());
		if (r_err_code == null) {
			response.setSign(r_sign.getStringValue());
			response.setTransactionId(r_transaction_id.getStringValue());
			response.setOutOrderNo(r_transaction_id.getStringValue());
			response.setTransactionId(r_out_order_no.getStringValue());
			response.setOrderId(r_order_id.getStringValue());
		} else {
			response.setErrCode(r_err_code.getStringValue());
			response.setErrCodeDes(r_err_code_des.getStringValue());
		}
		return response;
	}
}
