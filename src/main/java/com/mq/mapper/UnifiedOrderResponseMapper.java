package com.mq.mapper;

import com.mq.model.UnifiedOrderResponse;

public interface UnifiedOrderResponseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UnifiedOrderResponse record);

    int insertSelective(UnifiedOrderResponse record);

    UnifiedOrderResponse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UnifiedOrderResponse record);

    int updateByPrimaryKey(UnifiedOrderResponse record);
}