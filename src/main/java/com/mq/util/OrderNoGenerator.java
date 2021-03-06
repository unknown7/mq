package com.mq.util;

import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class OrderNoGenerator {
    private static final Object ORDER_NO_LOCK = "orderNoLock";
    private static final String ORDER_NO_PREFIX = "MQO";
    private static final String SHARING_NO_PREFIX = "MQS";
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private GlobalConstants globalConstants;

    public String next(String prefix) {
        synchronized (ORDER_NO_LOCK) {
            String key = Enums.RedisKey.ORDER_NUM_TODAY.getKey();
            String num = stringRedisTemplate.opsForValue().get(key);
            long incr;
            if (!StringUtils.isEmpty(num)) {
                incr = stringRedisTemplate.opsForValue().increment(key);
            } else {
                long seconds = DateUtil.getTomorrowZeroSeconds();
                incr = 1;
                stringRedisTemplate.opsForValue().set(key, String.valueOf(incr), seconds, TimeUnit.SECONDS);
            }
            StringBuilder builder = new StringBuilder(prefix);
            String curr = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
            builder.append(curr);
            long t = (incr << 1) + (incr << 3);
            long m = globalConstants.getOrdersLimitPerDay();
            while (t < m) {
                t = (t << 1) + (t << 3);
                builder.append("0");
            }
            builder.append(incr);
            return builder.toString();
        }
    }

    public String nextOrderNo() {
    	return next(ORDER_NO_PREFIX);
	}

	public String nextSharingNo() {
		return next(SHARING_NO_PREFIX);
	}
}
