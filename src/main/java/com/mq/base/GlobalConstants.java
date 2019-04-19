package com.mq.base;

import com.google.common.collect.Maps;
import com.mq.model.User;
import com.mq.vo.UserVo;

import java.util.Map;

public class GlobalConstants {
    public static String IMAGE_PATH;
    public static String VIDEO_PATH;
    /**
     * 用户缓存
     */
    public static final Map<String, UserVo> USER_CACHE = Maps.newConcurrentMap();
    /**
     * 微信开发账户信息
     */
    public static final String appId = "wxadfd8d2448611af5";
    public static final String appSecret = "6f4c76bbe365216e5ec900415274b15d";
    /**
     * 视频状态
     */
    public enum VideoStatus {
        UN_UPLOADED("0101", "未上传"),
        UN_RELEASED("0102", "未发布"),
        RELEASED("0103", "已发布");
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
        VIDEO("0101", "视频"),
        GOODS("0102", "商品");
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
}
