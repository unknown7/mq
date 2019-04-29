package com.mq.base;

import com.mq.util.DateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class OrderNoGenerator {
    private static final Object ORDER_NO_LOCK = "orderNoLock";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String next() {
        synchronized (ORDER_NO_LOCK) {
            String key = GlobalConstants.RedisKey.ORDER_NUM_TODAY.getKey();
            String num = stringRedisTemplate.opsForValue().get(key);
            long incr;
            if (!StringUtils.isEmpty(num)) {
                incr = stringRedisTemplate.opsForValue().increment(key);
            } else {
                long seconds = DateUtil.getTomorrowZeroSeconds();
                incr = 1;
                stringRedisTemplate.opsForValue().set(key, String.valueOf(incr), seconds, TimeUnit.SECONDS);
            }
            StringBuilder builder = new StringBuilder();
            String curr = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
            builder.append(curr);
            long t = (incr << 1) + (incr << 3);
            long m = GlobalConstants.ORDERS_LIMIT_PER_DAY;
            while (t < m) {
                t = (t << 1) + (t << 3);
                builder.append("0");
            }
            builder.append(incr);
            return builder.toString();
        }
    }
}
