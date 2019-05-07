package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.PaymentService;
import com.mq.service.RewardPointsService;
import com.mq.wx.vo.DefaultResponse;
import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Controller
@RequestMapping("/wx/payment")
public class PaymentController {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private static final String success = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
    private static final String fail = "<xml><return_code>FAIL</return_code><return_msg>FAIL</return_msg></xml>";
    @Resource
    private PaymentService paymentService;
    @Resource
    private RewardPointsService rewardPointsService;

    @RequestMapping("/purchase")
    @ResponseBody
    public String purchase(String skey,
                           Long videoId,
                           Boolean whetherUsePoints,
                           String usedPoints,
                           String price,
                           String originPrice,
                           String scene,
                           HttpServletRequest request)
    {
        DefaultResponse response;
        try {
            String remoteAddr = request.getRemoteAddr();
            logger.info("用户：" + skey + "购买商品：" + videoId + "，请求统一下单，scene=" + scene);
            UnifiedOrderVo unifiedOrderVo = paymentService.unifiedOrder(
                    skey,
                    videoId,
                    whetherUsePoints,
                    new BigDecimal(usedPoints),
                    new BigDecimal(price),
                    new BigDecimal(originPrice),
                    scene,
                    remoteAddr
            );
            response = DefaultResponse.success(unifiedOrderVo);
        } catch (Exception e) {
            logger.error("统一下单失败，skey=" + skey, e);
            response = DefaultResponse.fail("支付失败");
        }
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getPoints")
    @ResponseBody
    public String getPoints(String skey) {
        BigDecimal points = rewardPointsService.getPoints(skey);
        logger.info("用户：" + skey + "，积分：" + points);
        return JSON.toJSONString(points);
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public String paymentResult(HttpServletRequest request) {
        String resp;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            logger.info("支付结果通知，接收xml报文：" + builder);
            paymentService.paymentResultNotice(builder.toString());
            resp = success;
        } catch (Exception e) {
            logger.error("支付结果处理失败", e);
            resp = fail;
        }
        return resp;
    }
}
