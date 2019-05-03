package com.mq.mapper;

import com.mq.model.UnifiedOrderRequest;

public interface UnifiedOrderRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UnifiedOrderRequest record);

    int insertSelective(UnifiedOrderRequest record);

    UnifiedOrderRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UnifiedOrderRequest record);

    int updateByPrimaryKey(UnifiedOrderRequest record);
}