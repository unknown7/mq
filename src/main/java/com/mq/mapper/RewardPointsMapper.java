package com.mq.mapper;

import com.mq.model.RewardPoints;
import com.mq.query.RewardPointsQuery;

import java.math.BigDecimal;
import java.util.List;

public interface RewardPointsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RewardPoints record);

    int insertSelective(RewardPoints record);

    RewardPoints selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RewardPoints record);

    int updateByPrimaryKey(RewardPoints record);

    List<RewardPoints> selectByQuery(RewardPointsQuery query);

    Long selectNums(RewardPointsQuery query);

    BigDecimal getPoints(Long userId);
}