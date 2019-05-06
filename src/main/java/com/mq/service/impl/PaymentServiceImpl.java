package com.mq.service.impl;

import com.google.common.collect.Maps;
import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import com.mq.mapper.*;
import com.mq.model.*;
import com.mq.service.PaymentService;
import com.mq.service.UserService;
import com.mq.util.DateUtil;
import com.mq.util.MD5;
import com.mq.util.MapUtil;
import com.mq.util.OrderNoGenerator;
import com.mq.vo.VideoVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;
import jdk.nashorn.internal.objects.Global;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private OrderNoGenerator generator;
    @Resource
    private ShareCardMapper shareCardMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UnifiedOrderRequestMapper unifiedOrderRequestMapper;
    @Resource
    private UnifiedOrderResponseMapper unifiedOrderResponseMapper;
    @Resource
    private WxAPI wxAPI;
    @Resource
    private PaymentResultMapper paymentResultMapper;
    @Resource
    private RewardPointsMapper rewardPointsMapper;
    @Resource
    private GlobalConstants globalConstants;

    @Override
    @Transactional
    public UnifiedOrderVo unifiedOrder(String skey, Long videoId, String scene, String remoteAddr) throws Exception {
        assert !StringUtils.isEmpty(skey);
        assert videoId != null;
        User user = userService.getBySkey(skey);
        VideoVo videoVo = videoMapper.selectVoByPrimaryKey(Long.valueOf(videoId));
        Date now = new Date();

        Order order = new Order();
        order.setOrderNo(generator.next());
        order.setOrderStatus(Enums.OrderStatus.UNPAID.getKey());
        order.setGoodsId(videoId);
        order.setGoodsType(Enums.PurchaseType.VIDEO.getKey());
        order.setGoodsPrice(videoVo.getPrice());
        order.setUserId(user.getId());
        order.setTotalAmount(videoVo.getPrice());
        order.setWxAmount(videoVo.getPrice());
        order.setAccountBalanceAmount(BigDecimal.ZERO);
        if (!StringUtils.isEmpty(scene)) {
            Map<String, Object> sceneMap = MapUtil.param2map(scene);
            ShareCard shareCard = shareCardMapper.selectByPrimaryKey(Long.valueOf(sceneMap.get("shareCardId").toString()));
            Long referrer = shareCard.getUserId();
            order.setReferrer(referrer);
        }
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setDelFlag(0);
        orderMapper.insertSelective(order);

        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setAppid(globalConstants.getAppId());
        request.setMchId(globalConstants.getMchId());
        request.setNonceStr(MD5.generate(UUID.randomUUID().toString()));
        request.setNotifyUrl(globalConstants.getNotifyUrl());
        request.setOutTradeNo(order.getOrderNo());
        request.setTradeType(globalConstants.getTradeType());
        request.setBody("木荃孕产-" + videoVo.getClassificationName() + "-" + videoVo.getTitle());
        request.setOpenid(user.getOpenId());
        request.setTotalFee(Integer.valueOf(videoVo.getPrice().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString()));
        request.setSpbillCreateIp(remoteAddr);
        unifiedOrderRequestMapper.insertSelective(request);

        UnifiedOrderResponse unifiedOrderResponse = wxAPI.unifiedOrder(request);
        unifiedOrderResponseMapper.insertSelective(unifiedOrderResponse);

        Map<String, Object> signData = Maps.newLinkedHashMap();
        signData.put("appId", globalConstants.getAppId());
        signData.put("nonceStr", MD5.generate(UUID.randomUUID().toString()));
        signData.put("package", "prepay_id=" + unifiedOrderResponse.getPrepayId());
        signData.put("signType", "MD5");
        signData.put("timeStamp", System.currentTimeMillis() / 1000);
        signData.put("key", globalConstants.getApiKey());
        String paySign = MD5.generate(MapUtil.map2param(signData)).toUpperCase();
        UnifiedOrderVo unifiedOrderVo = new UnifiedOrderVo();
        unifiedOrderVo.setNonceStr(signData.get("nonceStr").toString());
        unifiedOrderVo.set_package(signData.get("package").toString());
        unifiedOrderVo.setPaySign(paySign);
        unifiedOrderVo.setSignType(signData.get("signType").toString());
        unifiedOrderVo.setTimeStamp(signData.get("timeStamp").toString());
        return unifiedOrderVo;
    }

    @Override
    @Transactional
    public void paymentResultNotice(String xml) throws Exception {
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        Element appid = root.element("appid");
        Element bankType = root.element("bank_type");
        Element cashFee = root.element("cash_fee");
        Element feeType = root.element("fee_type");
        Element isSubscribe = root.element("is_subscribe");
        Element mchId = root.element("mch_id");
        Element nonceStr = root.element("nonce_str");
        Element openid = root.element("openid");
        Element outTradeNo = root.element("out_trade_no");
        Element resultCode = root.element("result_code");
        Element returnCode = root.element("return_code");
        Element sign = root.element("sign");
        Element timeEnd = root.element("time_end");
        Element totalFee = root.element("total_fee");
        Element tradeType = root.element("trade_type");
        Element transactionId = root.element("transaction_id");
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setAppid(appid.getStringValue());
        paymentResult.setBankType(bankType.getStringValue());
        paymentResult.setCashFee(Integer.valueOf(cashFee.getStringValue()));
        paymentResult.setFeeType(feeType.getStringValue());
        paymentResult.setIsSubscribe(isSubscribe.getStringValue());
        paymentResult.setMchId(mchId.getStringValue());
        paymentResult.setNonceStr(nonceStr.getStringValue());
        paymentResult.setOpenid(openid.getStringValue());
        paymentResult.setOutTradeNo(outTradeNo.getStringValue());
        paymentResult.setResultCode(resultCode.getStringValue());
        paymentResult.setReturnCode(returnCode.getStringValue());
        paymentResult.setSign(sign.getStringValue());
        paymentResult.setTimeEnd(DateUtil.stringToDate(timeEnd.getStringValue(), "yyyyMMddHHmmss"));
        paymentResult.setTotalFee(Integer.valueOf(totalFee.getStringValue()));
        paymentResult.setTradeType(tradeType.getStringValue());
        paymentResult.setTransactionId(transactionId.getStringValue());
        paymentResultMapper.insertSelective(paymentResult);
        if ("SUCCESS".equals(paymentResult.getReturnCode())) {
            if ("SUCCESS".equals(paymentResult.getResultCode())) {
                Order order = orderMapper.selectByOrderNo(paymentResult.getOutTradeNo());
                order.setOrderStatus(Enums.OrderStatus.PAID.getKey());
                orderMapper.updateByPrimaryKeySelective(order);
                if (order.getReferrer() != null) {
                    User user = userMapper.selectByPrimaryKey(order.getReferrer());
                    ShareCard shareCard = shareCardMapper.selectOneByUserIdAndGoodsId(user.getId(), order.getGoodsId(), order.getGoodsType());
                    BigDecimal goodsPrice = shareCard.getGoodsPrice();
                    BigDecimal profitShare = shareCard.getProfitShare();
                    BigDecimal points = goodsPrice.multiply(profitShare);
                    RewardPoints rewardPoints = new RewardPoints();
                    rewardPoints.setPoints(points);
                    rewardPoints.setProfitFrom(order.getUserId());
                    rewardPoints.setRewardId(shareCard.getId());
                    rewardPoints.setRewardType(Enums.RewardType.SHARE.getKey());
                    rewardPoints.setUserId(user.getId());
                    Date now = new Date();
                    rewardPoints.setCreateTime(now);
                    rewardPoints.setUpdateTime(now);
                    rewardPoints.setDelFlag(0);
                    rewardPointsMapper.insertSelective(rewardPoints);
                }
            }
        }
    }
}
