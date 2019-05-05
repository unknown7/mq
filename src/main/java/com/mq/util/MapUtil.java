package com.mq.util;

import com.google.common.collect.Maps;

import java.util.Map;

public class MapUtil {
    public static String map2param(Map<String, Object> params) {
        return map2param(params, "&");
    }

    public static String map2param(Map<String, Object> params, String delimiter) {
        String result = "";
        if (params != null && !params.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            int index = 0;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
                if (++index != params.size()) {
                    buffer.append(delimiter);
                }
            }
            result = buffer.toString();
        }
        return result;
    }

    public static Map<String, Object> param2map(String params) {
        return param2map(params, "&");
    }

    public static Map<String, Object> param2map(String params, String delimiter) {
        Map<String, Object> result = Maps.newHashMap();
        String[] split = params.split(delimiter);
        for (String s : split) {
            String[] keyValue = s.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            result.put(key, value);
        }
        return result;
    }
}
