package com.mq.base;

import com.alibaba.fastjson.JSON;
import com.mq.vo.UserVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisObjectHolder {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setUserInfo(String skey, UserVo userVo) {
        assert userVo != null;
        stringRedisTemplate.opsForHash().put(
                Enums.RedisKey.USER_INFO.getKey(),
                skey,
                JSON.toJSONString(userVo)
        );
    }

    public void setUserInfo(Map<String, String> userVos) {
        if (userVos != null && !userVos.isEmpty()) {
            stringRedisTemplate.opsForHash().putAll(Enums.RedisKey.USER_INFO.getKey(), userVos);
        }
    }

    public UserVo getUserInfo(String skey) {
        UserVo userVo = null;
        Object o = stringRedisTemplate.opsForHash().get(Enums.RedisKey.USER_INFO.getKey(), skey);
        if (o != null) {
            userVo = JSON.parseObject(o.toString(), UserVo.class);
        }
        return userVo;
    }

    public void setAccessToken(String accessToken, Integer expiresIn) {
        assert !StringUtils.isEmpty(accessToken);
        stringRedisTemplate.opsForValue().set(
                Enums.RedisKey.ACCESS_TOKEN_KEY.getKey(),
                accessToken,
                expiresIn,
                TimeUnit.SECONDS
        );
    }

    public String getAccessToken() {
        String accessToken = stringRedisTemplate.opsForValue().get(Enums.RedisKey.ACCESS_TOKEN_KEY.getKey());
        return accessToken;
    }

    public void setTemporaryUser(String skey) {
        assert !StringUtils.isEmpty(skey);
        stringRedisTemplate.opsForHash().put(Enums.RedisKey.TEMPORARY_USER.getKey(), skey, "");
    }

    public boolean isTemporaryUser(String skey) {
        return stringRedisTemplate.opsForHash().hasKey(Enums.RedisKey.TEMPORARY_USER.getKey(), skey);
    }

    public void delTemporaryUser(String skey) {
        stringRedisTemplate.opsForHash().delete(Enums.RedisKey.TEMPORARY_USER.getKey(), skey);
    }
}
