package com.mq.base;

public class Enums {
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
        WHITE_LIST("white_list"),
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

    /**
     * 积分状态
     */
    public enum PointsStatus {
        UNUSED("006001", "未使用"),
        USED("006002", "已使用"),
        ;
        private String key;
        private String value;
        PointsStatus(String key, String value) {
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
	 * 邀请单状态
	 */
	public enum InvitationStatus {
		SCANNED("007001", "已扫码"),
		REGISTERED("007002", "已注册"),
		PURCHASED("007003", "已购买"),
		USED("007004", "已使用"),
		APPLIED("007005", "已申请提现"),
		AGREED("007006", "已同意提现"),
		REJECTED("007007", "已拒绝提现"),
		SUCCESS("007008", "提现成功"),
		FAILURE("007009", "提现失败"),
		;
		private String key;
		private String value;
		InvitationStatus(String key, String value) {
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
