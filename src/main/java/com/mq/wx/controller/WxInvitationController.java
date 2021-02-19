package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.InvitationRecordService;
import com.mq.vo.InvitationRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wx/invitation")
public class WxInvitationController {

    protected static final Logger logger = LoggerFactory.getLogger(WxInvitationController.class);

    @Resource
    private InvitationRecordService invitationRecordService;

    @RequestMapping("/findInvitations")
    public String findInvitations(String skey) {
        List<InvitationRecordVo> invitationRecords = invitationRecordService.findByInviterSkey(skey);
        return JSON.toJSONString(invitationRecords);
    }
}
