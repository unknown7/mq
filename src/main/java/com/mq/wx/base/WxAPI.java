package com.mq.wx.base;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.base.Http;
import com.mq.util.DateUtil;
import com.mq.util.MD5Util;
import com.mq.wx.vo.accessToken.AccessTokenResponse;
import com.mq.wx.vo.auth.AuthResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信API调用
 */

@Component
public class WxAPI {
    @javax.annotation.Resource
    private Http http;
    @javax.annotation.Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取access_token，如果缓存中存在，直接返回，如果不存在，调用微信接口，获取token并存入缓存
     *
     * @return access_token
     */
    public String getAccessToken() {
        String accessToken = stringRedisTemplate.opsForValue().get(GlobalConstants.RedisKey.ACCESS_TOKEN_KEY.getKey());
        if (StringUtils.isEmpty(accessToken)) {
            String url = "https://api.weixin.qq.com/cgi-bin/token";
            Map<String, Object> params = Maps.newHashMap();
            params.put("grant_type", GlobalConstants.GrantType.CLIENT_CREDENTIAL.getKey());
            params.put("appid", GlobalConstants.APP_ID);
            params.put("secret", GlobalConstants.APP_SECRET);
            String result = http.get(url, params);
            if (!StringUtils.isEmpty(result)) {
                AccessTokenResponse response = JSONObject.parseObject(result, AccessTokenResponse.class);
                if (!StringUtils.isEmpty(response.getAccess_token())) {
                    accessToken = response.getAccess_token();
                    stringRedisTemplate.opsForValue().set(
                            GlobalConstants.RedisKey.ACCESS_TOKEN_KEY.getKey(),
                            response.getAccess_token(),
                            response.getExpires_in(),
                            TimeUnit.SECONDS
                    );
                }
            }
        }
        return accessToken;
    }

    /**
     * 生成小程序码
     *
     * @param page
     * @param scene
     * @return
     */
    public String getUnlimited(String page, Map<String, Object> scene) {
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
        Map<String, Object> params = Maps.newHashMap();
        params.put("page", page);
        params.put("scene", http.map2str(scene));
        Resource result = http.post2Resource(url, params);
        String path;
        InputStream responseInputStream;
        try {
            responseInputStream = result.getInputStream();
            Date now = new Date();
            String name = MD5Util.getEncryption(DateUtil.dateToString(now, DateUtil.DATE_TIME_FORMAT));
            path = GlobalConstants.IMAGE_PATH.concat(name).concat(".jpg");
            FileOutputStream fos = new FileOutputStream(path);
            byte[] b = new byte[1024];
            int length;
            while ((length = responseInputStream.read(b)) > 0) {
                fos.write(b, 0, length);
            }
            responseInputStream.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    /**
     * 获取用户认证结果
     * @param code
     * @return
     */
    public AuthResponse jscode2session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> params = Maps.newHashMap();
        params.put("grant_type", GlobalConstants.GrantType.AUTHORIZATION_CODE.getKey());
        params.put("appid", GlobalConstants.APP_ID);
        params.put("secret", GlobalConstants.APP_SECRET);
        params.put("js_code", code);
        String result = http.get(url, params);
        AuthResponse authResponse = JSONObject.parseObject(result, AuthResponse.class);
        return authResponse;
    }
}
