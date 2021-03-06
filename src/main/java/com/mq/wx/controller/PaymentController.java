package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.PaymentService;
import com.mq.service.RewardPointsService;
import com.mq.wx.vo.DefaultResponse;
import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("/wx/payment")
public class PaymentController {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Resource
    private PaymentService paymentService;
    @Resource
    private RewardPointsService rewardPointsService;

    @RequestMapping("/purchase")
    public String purchase(String skey,
                           Long videoId,
                           Boolean whetherUsePoints,
                           String usedPoints,
                           String price,
                           String originPrice,
                           HttpServletRequest request)
    {
        DefaultResponse response;
        try {
            String remoteAddr = request.getRemoteAddr();
            logger.info("用户：" + skey + "购买商品：" + videoId + "，请求统一下单");
            UnifiedOrderVo unifiedOrderVo = paymentService.unifiedOrder(
                    skey,
                    videoId,
                    whetherUsePoints,
                    new BigDecimal(usedPoints),
                    new BigDecimal(price),
                    new BigDecimal(originPrice),
                    remoteAddr
            );
            response = DefaultResponse.success(unifiedOrderVo);
        } catch (Exception e) {
            logger.error("统一下单失败，skey=" + skey, e);
            response = DefaultResponse.fail("支付失败");
            if ("invalid_param_points".equals(e.getMessage()) || "invalid_param_price".equals(e.getMessage())) {
                response = DefaultResponse.create(false, e.getMessage());
            }
        }
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getPoints")
    public String getPoints(String skey) {
        BigDecimal points = rewardPointsService.getPoints(skey);
        logger.info("用户：" + skey + "，积分：" + points);
        return JSON.toJSONString(points);
    }
}
