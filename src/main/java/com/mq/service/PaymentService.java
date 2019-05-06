package com.mq.service;

import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;

public interface PaymentService {
    UnifiedOrderVo unifiedOrder(String skey, Long videoId, String scene, String remoteAddr) throws Exception;
    void paymentResultNotice(String xml) throws Exception;
}
