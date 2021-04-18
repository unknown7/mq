package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.model.AboutUs;
import com.mq.service.BasicConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/basicConfig")
public class WxBasicConfigController {

	protected static final Logger logger = LoggerFactory.getLogger(WxBasicConfigController.class);

	@Resource
	private BasicConfigService basicConfigService;

	@RequestMapping("/aboutUs")
	public String getAboutUs() {
		AboutUs aboutUs = basicConfigService.getAboutUs();
		return JSON.toJSONString(aboutUs);
	}
}
