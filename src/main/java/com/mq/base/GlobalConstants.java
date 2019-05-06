package com.mq.base;

import org.springframework.beans.factory.annotation.Value;

public class GlobalConstants {
    /**
     * 静态资源路径
     */
    public static String IMAGE_PATH;
    public static String VIDEO_PATH;

    /**
     * 微信基本信息
     */
    public static String APP_ID;
    public static String APP_SECRET;
    public static String MCH_ID;
    public static String API_KEY;
    public static String NOTIFY_URL;
    public static String TRADE_TYPE;
    @Value("${constants.app-id}")
    public static void setAppId(String appId) {
        APP_ID = appId;
    }
    @Value("${constants.app-secret}")
    public static void setAppSecret(String appSecret) {
        APP_SECRET = appSecret;
    }
    @Value("${constants.mch-id}")
    public static void setMchId(String mchId) {
        MCH_ID = mchId;
    }
    @Value("${constants.api-key}")
    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
    @Value("${constants.notify-url}")
    public static void setNotifyUrl(String notifyUrl) {
        NOTIFY_URL = notifyUrl;
    }
    @Value("${constants.trade-type}")
    public static void setTradeType(String tradeType) {
        TRADE_TYPE = tradeType;
    }

    /**
     * 订单号生成器信息
     */
    public static Long ORDERS_LIMIT_PER_DAY;
    @Value("${constants.orders-limit-per-day}")
    public static void setOrdersLimitPerDay(String ordersLimitPerDay) {
        APP_ID = ordersLimitPerDay;
    }

    /**
     * 微信api类型
     */
    public enum GrantType {
        CLIENT_CREDENTIAL("client_credential"),
        AUTHORIZATION_CODE("authorization_code"),
        ;
        private String key;
        GrantType(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
    }

    /**
     * Redis Key
     */
    public enum RedisKey {
        ACCESS_TOKEN_KEY("access_token"),
        USER_INFO("user_info"),
        TEMPORARY_USER("temporary_user"),
        ORDER_NUM_TODAY("order_num_today"),
        ;
        private String key;
        RedisKey(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
    }

    /**
     * 视频状态
     */
    public enum VideoStatus {
        UN_UPLOADED("001001", "未上传"),
        UN_RELEASED("001002", "未发布"),
        RELEASED("001003", "已发布"),
        ;
        private String key;
        private String value;
        VideoStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 商品购买类型
     */
    public enum PurchaseType {
        VIDEO("002001", "视频"),
        GOODS("002002", "商品"),
        ;
        private String key;
        private String value;
        PurchaseType(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 统计类型
     */
    public enum StatisticsType {
        PV("003001", "PV量"),
        UV("003002", "UV量"),
        VIDEO_WATCHED("003003", "视频播放量"),
        VIDEO_PURCHASED("003004", "视频购买量"),
        VIDEO_ACCESSED("003005", "视频访问量"),
        GOODS_PURCHASED("003006", "商品购买量"),
        GOODS_ACCESSED("003007", "商品访问量"),
        ;
        private String key;
        private String value;
        StatisticsType(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 订单状态
     */
    public enum OrderStatus {
        UNPAID("004001", "待付款"),
        PAID("004002", "已付款"),
        ;
        private String key;
        private String value;
        OrderStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 奖励类型
     */
    public enum RewardType {
        SHARE("005001", "分享奖励"),
        ;
        private String key;
        private String value;
        RewardType(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }
}
