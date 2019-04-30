package com.mq.base;

public class GlobalConstants {
    public static String IMAGE_PATH;
    public static String VIDEO_PATH;
    public static final Long ORDERS_LIMIT_PER_DAY = 1000000L;
    /**
     * 微信基本信息-魏峰
     */
//    public static final String APP_ID = "wxadfd8d2448611af5";
//    public static final String APP_SECRET = "6f4c76bbe365216e5ec900415274b15d";
    /**
     * 微信基本信息-木荃孕产
     */
    public static final String APP_ID = "wx153c1bb866bfece8";
    public static final String APP_SECRET = "4688af737cf288429c8180d3db6ae21e";
    public static final String MCH_ID = "1533502711";
    public static final String API_KEY = "a02ec5baf0987e3857dd1980c5602252";
    public static final String NOTIFY_URL = "https://www.mqyoga.cn/mq/wx/";
    public static final String TRADE_TYPE = "JSAPI";
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
     * Redis
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
}
