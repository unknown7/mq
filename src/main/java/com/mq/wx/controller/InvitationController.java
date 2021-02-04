package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.InvitationRecordService;
import com.mq.wx.vo.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/invitation")
public class InvitationController {

	protected static final Logger logger = LoggerFactory.getLogger(InvitationController.class);

	@Resource
	private InvitationRecordService invitationRecordService;

	@RequestMapping("/init")
	public String init(String skey, Long shareCardId) {
		DefaultResponse response;
		try {
			invitationRecordService.initRecord(shareCardId, skey);
			response = DefaultResponse.success(null);
		} catch (Exception e) {
			logger.error("初始化邀请信息失败，skey=" + skey, e);
			response = DefaultResponse.fail("初始化邀请信息失败");
		}
		return JSON.toJSONString(response);

	}
}
