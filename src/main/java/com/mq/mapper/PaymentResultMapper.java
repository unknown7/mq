package com.mq.mapper;

import com.mq.model.PaymentResult;
import org.apache.ibatis.annotations.Param;

public interface PaymentResultMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentResult record);

    int insertSelective(PaymentResult record);

    PaymentResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentResult record);

    int updateByPrimaryKey(PaymentResult record);

    PaymentResult selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);
}