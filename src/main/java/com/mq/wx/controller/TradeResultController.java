package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.model.TradeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/wx")
public class TradeResultController {
    protected static final Logger logger = LoggerFactory.getLogger(TradeResultController.class);

    @RequestMapping(value = "/tradeResult", method = RequestMethod.POST)
    @ResponseBody
    public String tradeResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        logger.info("支付结果通知，接收xml报文：" + sb);
        return null;
    }
}
