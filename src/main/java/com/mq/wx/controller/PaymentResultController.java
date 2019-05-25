package com.mq.wx.controller;

import com.mq.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/wx")
public class PaymentResultController {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentResultController.class);
    private static final String SUCCESS = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
    private static final String FAIL = "<xml><return_code>FAIL</return_code><return_msg>FAIL</return_msg></xml>";
    @Resource
    private PaymentService paymentService;


    @RequestMapping(value = "/paymentResult", method = RequestMethod.POST)
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
            resp = SUCCESS;
        } catch (Exception e) {
            logger.error("支付结果处理失败", e);
            resp = FAIL;
        }
        return resp;
    }
}
