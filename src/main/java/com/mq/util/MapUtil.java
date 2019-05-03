package com.mq.util;

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
}
