package com.mq.mapper;

import com.mq.model.PaymentResult;

public interface PaymentResultMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentResult record);

    int insertSelective(PaymentResult record);

    PaymentResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentResult record);

    int updateByPrimaryKey(PaymentResult record);
}
