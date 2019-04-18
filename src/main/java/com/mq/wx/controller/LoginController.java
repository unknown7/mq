package com.mq.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.mq.base.GlobalConstants;
import com.mq.util.WxDecrptUtil;
import com.mq.wx.vo.LoginResponse;
import com.mq.wx.vo.LoginRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Controller("wxLogin")
@RequestMapping("/wx")
public class LoginController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/login")
    @ResponseBody
    public String login(LoginRequest request) {
        StringBuffer uri = new StringBuffer();
        uri.append("https://api.weixin.qq.com/sns/jscode2session")
            .append("?appid=").append(GlobalConstants.appId)
            .append("&secret=").append(GlobalConstants.appSecret)
            .append("&js_code=").append(request.getCode())
            .append("&grant_type=authorization_code")
        ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(headers);
        String result = restTemplate.exchange(
                uri.toString(),
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();
        LoginResponse loginResponse = JSONObject.parseObject(result, LoginResponse.class);
        JSONObject userInfo = WxDecrptUtil.getUserInfo(request.getEncryptedData(), loginResponse.getSession_key(), request.getIv());
        System.err.println(userInfo);


        return null;
    }
}
