package com.mq.mapper;

import com.mq.model.TradeResult;

public interface TradeResultMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeResult record);

    int insertSelective(TradeResult record);

    TradeResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeResult record);

    int updateByPrimaryKey(TradeResult record);
}