package com.mq.service.impl;

import com.mq.mapper.VerifySwitchMapper;
import com.mq.model.VerifySwitch;
import com.mq.service.VerifySwitchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VerifySwitchServiceImpl implements VerifySwitchService {
    @Resource
    private VerifySwitchMapper verifySwitchMapper;
    @Override
    public Boolean getVerifySwitch() {
        VerifySwitch verifySwitch = verifySwitchMapper.selectByPrimaryKey(1);
        return verifySwitch.getVerifySwitch();
    }
}
