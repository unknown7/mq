package com.mq.service.impl;

import com.mq.base.Enums;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.StatisticsMapper;
import com.mq.model.Statistics;
import com.mq.service.StatisticsService;
import com.mq.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private RedisObjectHolder redisObjectHolder;

    @Override
    @Transactional
    public void watchVideo(String skey, Long id) {
        if (!redisObjectHolder.isWhiteUser(skey)) {
            Statistics statistics = build(skey, id);
            statistics.setStatisticsType(Enums.StatisticsType.VIDEO_WATCHED.getKey());
            statisticsMapper.insertSelective(statistics);
        }
    }

    @Override
    @Transactional
    public void purchaseVideo(String skey, Long id) {
        if (!redisObjectHolder.isWhiteUser(skey)) {
            Statistics statistics = build(skey, id);
            statistics.setStatisticsType(Enums.StatisticsType.VIDEO_PURCHASED.getKey());
            statisticsMapper.insertSelective(statistics);
        }
    }

    @Override
    @Transactional
    public void accessVideo(String skey, Long id) {
        if (!redisObjectHolder.isWhiteUser(skey)) {
            Statistics statistics = build(skey, id);
            statistics.setStatisticsType(Enums.StatisticsType.VIDEO_ACCESSED.getKey());
            statisticsMapper.insertSelective(statistics);
        }
    }

    private Statistics build(String skey, Long id) {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        Date now = new Date();
        Statistics statistics = new Statistics();
        statistics.setBusinessId(id);
        statistics.setUserId(userVo != null ? userVo.getId() : null);
        statistics.setSkey(skey);
        statistics.setCreatedTime(now);
        statistics.setModifiedTime(now);
        statistics.setDelFlag(Boolean.FALSE);
        return statistics;
    }
}
