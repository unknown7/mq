package com.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import com.mq.mapper.*;
import com.mq.model.*;
import com.mq.service.InvitationRecordService;
import com.mq.service.PaymentService;
import com.mq.service.StatisticsService;
import com.mq.service.UserService;
import com.mq.util.DateUtil;
import com.mq.util.SignUtil;
import com.mq.util.MapUtil;
import com.mq.util.OrderNoGenerator;
import com.mq.vo.UserVo;
import com.mq.vo.VideoVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

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
    @Resource
    private StatisticsService statisticsService;
    @Resource
	private InvitationRecordService invitationRecordService;

    @Override
    @Transactional
    public UnifiedOrderVo unifiedOrder(String skey,
                                       Long videoId,
                                       Boolean whetherUsePoints,
                                       BigDecimal usedPoints,
                                       BigDecimal price,
                                       BigDecimal originPrice,
                                       String remoteAddr) throws Exception
    {
        assert !StringUtils.isEmpty(skey);
        assert videoId != null;
        User user = userService.getBySkey(skey);
        VideoVo videoVo = videoMapper.selectVoByPrimaryKey(videoId);
        BigDecimal points = rewardPointsMapper.getPoints(user.getId());
        validatePaymentParam(videoVo, points, whetherUsePoints, usedPoints, price, originPrice);
        Date now = new Date();

        Order order = new Order();
        order.setOrderNo(generator.nextOrderNo());
        order.setOrderStatus(Enums.OrderStatus.UNPAID.getKey());
        order.setGoodsId(videoId);
        order.setGoodsType(Enums.PurchaseType.VIDEO.getKey());
        /**
         * 是否使用积分抵扣
         */
        if (whetherUsePoints) {
            order.setTotalAmount(videoVo.getPrice());
            order.setPayAmount(videoVo.getPrice().subtract(points));
            order.setPoints(points);
        } else {
            order.setTotalAmount(videoVo.getPrice());
            order.setPayAmount(videoVo.getPrice());
            order.setPoints(BigDecimal.ZERO);
        }
        order.setGoodsPrice(videoVo.getPrice());
        order.setUserId(user.getId());
        order.setSkey(skey);

		Predicate<InvitationRecord> invitation = new Predicate<InvitationRecord>() {
			private final List<String> LIST = Lists.newArrayList(
					Enums.InvitationStatus.SCANNED.getKey(),
					Enums.InvitationStatus.REGISTERED.getKey()
			);
			@Override
			public boolean test(InvitationRecord invitationRecord) {
				return invitationRecord != null && LIST.contains(invitationRecord.getStatus());
			}
		};

		InvitationRecord invitationRecord = invitationRecordService.getByInviteeIdAndGoodsId(
				user.getId(),
				videoId,
				Enums.PurchaseType.VIDEO.getKey()
		);
		if (invitation.test(invitationRecord)) {
			order.setReferrer(invitationRecord.getInviterId());
			order.setInvitationId(invitationRecord.getId());
        }
        order.setCreatedTime(now);
        order.setModifiedTime(now);
        order.setDelFlag(Boolean.FALSE);
        orderMapper.insertSelective(order);

        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setAppid(globalConstants.getAppId());
        request.setMchId(globalConstants.getMchId());
        request.setNonceStr(SignUtil.md5(UUID.randomUUID().toString()));
        request.setNotifyUrl(globalConstants.getNotifyUrl());
        request.setOutTradeNo(order.getOrderNo());
        request.setTradeType(globalConstants.getTradeType());
        request.setBody("木荃瑜伽-" + videoVo.getClassificationName() + "-" + videoVo.getTitle());
        request.setOpenid(user.getOpenId());
        request.setTotalFee(Integer.valueOf(order.getPayAmount().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString()));
        request.setSpbillCreateIp(remoteAddr);
        /**
         * 是否使用积分抵扣
         */
        if (whetherUsePoints) {
            Map<String, String> params = Maps.newHashMap();
            params.put("unifiedOrderTime", DateUtil.dateToString(now, "yyyyMMddHHmmss"));
            request.setAttach(JSON.toJSONString(params));
        }
		/**
		 * 是否为业务员推荐
		 */
		if (invitation.test(invitationRecord)) {
			User saleManager = userService.getById(invitationRecord.getInviterId());
			UserVo saleManagerVo = userService.getVoBySkey(saleManager.getSkey());
			if (saleManagerVo.getIsEmployee()) {
				request.setProfitSharing("Y");
			}
		}
		unifiedOrderRequestMapper.insertSelective(request);
        /**
         * 统一下单接口
         */
        UnifiedOrderResponse unifiedOrderResponse = wxAPI.unifiedOrder(request);
        unifiedOrderResponseMapper.insertSelective(unifiedOrderResponse);

        Map<String, Object> signData = Maps.newLinkedHashMap();
        signData.put("appId", globalConstants.getAppId());
        signData.put("nonceStr", SignUtil.md5(UUID.randomUUID().toString()));
        signData.put("package", "prepay_id=" + unifiedOrderResponse.getPrepayId());
        signData.put("signType", "SignUtil");
        signData.put("timeStamp", System.currentTimeMillis() / 1000);
        signData.put("key", globalConstants.getApiKey());
        String paySign = SignUtil.md5(MapUtil.map2str(signData)).toUpperCase();
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
        /**
         * 验签
         */
        Map<String, Object> signData = Maps.newTreeMap();
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            if (!next.getName().equals("sign")) {
                signData.put(next.getName(), next.getStringValue());
            }
        }
        String s = MapUtil.map2str(signData);
        s = s + "&key=" + globalConstants.getApiKey();
        Element sign = root.element("sign");
        Assert.isTrue(SignUtil.md5(s).toUpperCase().equals(sign.getStringValue()), "验签失败");

        Element appid = root.element("appid");
        Element attach = root.element("attach");
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
        Element timeEnd = root.element("time_end");
        Element totalFee = root.element("total_fee");
        Element tradeType = root.element("trade_type");
        Element transactionId = root.element("transaction_id");
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setAppid(appid.getStringValue());
        paymentResult.setAttach(attach != null ? attach.getStringValue() : null);
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
                /**
                 * 锁订单号
                 */
                synchronized (paymentResult.getOutTradeNo()) {
                    Order order = orderMapper.selectByOrderNo(paymentResult.getOutTradeNo());
                    if (Enums.OrderStatus.UNPAID.getKey().equals(order.getOrderStatus())) {
						Date now = new Date();
                        order.setOrderStatus(Enums.OrderStatus.PAID.getKey());
                        order.setModifiedTime(now);
                        orderMapper.updateByPrimaryKeySelective(order);
                        /**
                         * 统计已购买
                         */
                        statisticsService.purchaseVideo(order.getSkey(), order.getGoodsId());
                        /**
                         * 推荐人奖励
                         */
                        if (order.getReferrer() != null) {
                            User user = userMapper.selectByPrimaryKey(order.getReferrer());
							UserVo userVo = userService.getVoBySkey(user.getSkey());
							ShareCard shareCard = shareCardMapper.selectOneByUserIdAndGoodsId(user.getId(), order.getGoodsId(), order.getGoodsType());
                            BigDecimal goodsPrice = shareCard.getGoodsPrice();
                            BigDecimal profitPercent;
                            if (userVo.getIsEmployee()) {
								profitPercent = shareCard.getProfitSale();
							} else {
								profitPercent = shareCard.getProfitShare();
							}
                            BigDecimal points = goodsPrice.multiply(profitPercent);
                            RewardPoints rewardPoints = new RewardPoints();
                            rewardPoints.setPoints(points);
                            rewardPoints.setPointsStatus(Enums.PointsStatus.UNUSED.getKey());
                            rewardPoints.setProfitFrom(order.getUserId());
                            rewardPoints.setRewardId(shareCard.getId());
                            rewardPoints.setRewardType(Enums.RewardType.SHARE.getKey());
                            rewardPoints.setUserId(user.getId());
                            rewardPoints.setCreatedTime(now);
                            rewardPoints.setModifiedTime(now);
                            rewardPoints.setDelFlag(Boolean.FALSE);
                            rewardPointsMapper.insertSelective(rewardPoints);

							invitationRecordService.purchased(order.getInvitationId());
						}
                        /**
                         * 扣取积分
                         */
                        if (!StringUtils.isEmpty(paymentResult.getAttach())) {
                            Map<String, String> attachMap = JSON.parseObject(paymentResult.getAttach(), Map.class);
                            String unifiedOrderTimeStr = attachMap.get("unifiedOrderTime");
                            Date unifiedOrderTime = DateUtil.stringToDate(unifiedOrderTimeStr, "yyyyMMddHHmmss");
                            List<Long> ids = rewardPointsMapper.getUnusedPointsBefore(order.getUserId(), unifiedOrderTime);
                            rewardPointsMapper.batchUpdateStatus(ids, Enums.PointsStatus.USED.getKey());
							invitationRecordService.usePoints(order.getUserId(), unifiedOrderTime);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param videoVo           服务器视频信息
     * @param points            服务器积分信息
     * @param whetherUsePoints  是否使用积分抵扣
     * @param usedPoints        小程序端传入的积分
     * @param price             小程序端传入的最终价格
     * @param originPrice       小程序端传入的原始价格
     */
    private void validatePaymentParam(VideoVo videoVo,
                                      BigDecimal points,
                                      Boolean whetherUsePoints,
                                      BigDecimal usedPoints,
                                      BigDecimal price,
                                      BigDecimal originPrice)
    {
        Assert.isTrue(videoVo.getPrice().compareTo(originPrice) == 0, "invalid_param_price");
        if (whetherUsePoints) {
            Assert.isTrue(usedPoints.compareTo(points) == 0, "invalid_param_points");
            Assert.isTrue(originPrice.subtract(usedPoints).compareTo(price) == 0, "invalid_param_price");
            Assert.isTrue(videoVo.getPrice().subtract(points).compareTo(price) == 0, "invalid_param_price");
        } else {
            Assert.isTrue(videoVo.getPrice().compareTo(price) == 0, "invalid_param_price");
        }
    }
}
