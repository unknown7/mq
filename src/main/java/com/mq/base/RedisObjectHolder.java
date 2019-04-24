package com.mq.base;

import com.mq.vo.UserVo;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.security.provider.MD5;
import sun.security.rsa.RSASignature;

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
                GlobalConstants.RedisKey.USER_INFO.getKey(),
                skey,
                userVo
        );
    }

    public void setUserInfo(Map<String, UserVo> userVos) {
        assert userVos != null && !userVos.isEmpty();
        stringRedisTemplate.opsForHash().putAll(GlobalConstants.RedisKey.USER_INFO.getKey(), userVos);
    }

    public UserVo getUserInfo(String skey) {
        Object o = stringRedisTemplate.opsForHash().get(GlobalConstants.RedisKey.USER_INFO.getKey(), skey);
        UserVo userVo = o != null ? (UserVo) o : null;
        return userVo;
    }

    public void setAccessToken(String accessToken, Integer expiresIn) {
        assert !StringUtils.isEmpty(accessToken);
        stringRedisTemplate.opsForValue().set(
                GlobalConstants.RedisKey.ACCESS_TOKEN_KEY.getKey(),
                accessToken,
                expiresIn,
                TimeUnit.SECONDS
        );
    }

    public String getAccessToken() {
        String accessToken = stringRedisTemplate.opsForValue().get(GlobalConstants.RedisKey.ACCESS_TOKEN_KEY.getKey());
        return accessToken;
    }
}
