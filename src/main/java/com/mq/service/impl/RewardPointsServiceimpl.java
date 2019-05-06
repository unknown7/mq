package com.mq.service.impl;

import com.mq.base.RedisObjectHolder;
import com.mq.mapper.RewardPointsMapper;
import com.mq.service.RewardPointsService;
import com.mq.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class RewardPointsServiceimpl implements RewardPointsService {
    @Resource
    private RewardPointsMapper rewardPointsMapper;
    @Resource
    private RedisObjectHolder redisObjectHolder;

    @Override
    public BigDecimal getPoints(String skey) {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        BigDecimal points = rewardPointsMapper.getPoints(userVo.getId());
        return points;
    }
}
