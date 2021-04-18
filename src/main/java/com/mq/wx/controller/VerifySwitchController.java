package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.VerifySwitchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class VerifySwitchController {
    @Resource
    private VerifySwitchService verifySwitchService;

    @RequestMapping("/wx/getButton")
    public String getButton() {
        Boolean verifySwitch = verifySwitchService.getVerifySwitch();
        return JSON.toJSONString(verifySwitch);
    }
}
