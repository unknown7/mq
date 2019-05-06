package com.mq.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalConstants {
    /**
     * 静态资源路径
     */
    public static String IMAGE_PATH;
    public static String VIDEO_PATH;

    /**
     * 微信基本信息
     */
    @Value("${global-constants.app-id}")
    public String appId;
    @Value("${global-constants.app-secret}")
    public String appSecret;
    @Value("${global-constants.mch-id}")
    public String mchId;
    @Value("${global-constants.api-key}")
    public String apiKey;
    @Value("${global-constants.notify-url}")
    public String notifyUrl;
    @Value("${global-constants.trade-type}")
    public String tradeType;

    /**
     * 订单号生成器信息
     */
    public Long ordersLimitPerDay;
    @Value("${global-constants.orders-limit-per-day}")
    public void setOrdersLimitPerDay(String limit) {
        ordersLimitPerDay = Long.valueOf(limit);
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public Long getOrdersLimitPerDay() {
        return ordersLimitPerDay;
    }
}
