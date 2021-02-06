package com.mq.service;

import com.mq.model.RewardPoints;

import java.math.BigDecimal;

public interface RewardPointsService {
    BigDecimal getPoints(String skey);

    RewardPoints getByOrderId(Long orderId);

    void modify(RewardPoints rewardPoints);
}
