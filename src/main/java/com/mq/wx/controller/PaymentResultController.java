package com.mq.wx.controller;

import com.mq.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/wx")
public class PaymentResultController {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentResultController.class);
    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "/paymentResult", method = RequestMethod.POST)
    @ResponseBody
    public String paymentResult(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = br.readLine())!=null) {
                builder.append(line);
            }
            logger.info("支付结果通知，接收xml报文：" + builder);
            paymentService.paymentResultNotice(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
