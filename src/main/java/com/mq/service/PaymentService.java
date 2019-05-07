package com.mq.service;

import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;

import java.math.BigDecimal;

public interface PaymentService {
    UnifiedOrderVo unifiedOrder(String skey,
                                Long videoId,
                                Boolean whetherUsePoints,
                                BigDecimal usedPoints,
                                BigDecimal price,
                                BigDecimal originPrice,
                                String scene,
                                String remoteAddr) throws Exception;
    void paymentResultNotice(String xml) throws Exception;
}
