package com.mq.wx.base;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.base.Http;
import com.mq.base.RedisObjectHolder;
import com.mq.util.MD5Util;
import com.mq.wx.vo.accessToken.AccessTokenResponse;
import com.mq.wx.vo.auth.AuthResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 微信API调用
 */

@Component
public class WxAPI {
    @javax.annotation.Resource
    private Http http;
    @javax.annotation.Resource
    private RedisObjectHolder redisObjectHolder;

    /**
     * 获取access_token，如果缓存中存在，直接返回，如果不存在，调用微信接口，获取token并存入缓存
     *
     * @return access_token
     */
    public String getAccessToken() {
        String accessToken = redisObjectHolder.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            String domain = "https://api.weixin.qq.com/cgi-bin/token";
            Map<String, Object> params = Maps.newHashMap();
            params.put("appid", GlobalConstants.APP_ID);
            params.put("secret", GlobalConstants.APP_SECRET);
            params.put("grant_type", GlobalConstants.GrantType.CLIENT_CREDENTIAL.getKey());
            ResponseEntity<String> responseEntity = http.getForEntity(domain, params, String.class);
            String result = responseEntity.getBody();
            if (!StringUtils.isEmpty(result)) {
                AccessTokenResponse response = JSONObject.parseObject(result, AccessTokenResponse.class);
                if (!StringUtils.isEmpty(response.getAccess_token())) {
                    accessToken = response.getAccess_token();
                    redisObjectHolder.setAccessToken(response.getAccess_token(), response.getExpires_in());
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
        StringBuffer domain = new StringBuffer("https://api.weixin.qq.com/wxa/getwxacodeunlimit");
        domain.append("?access_token=").append(accessToken);
        Map<String, Object> params = Maps.newHashMap();
//        params.put("page", page);
        params.put("scene", http.map2param(scene));
        ResponseEntity<Resource> responseEntity = http.postForEntity(domain.toString(), params, Resource.class);
        MediaType contentType = responseEntity.getHeaders().getContentType();
        System.err.println(contentType);
        String path = null;
        /**
         * 成功的contentType为image/jpeg
         * 失败的contentType为application/json
         */
        if (MediaType.IMAGE_JPEG.equals(contentType)) {
            try {
                InputStream is = responseEntity.getBody().getInputStream();
                String name = MD5Util.getEncryption(String.valueOf(System.currentTimeMillis()));
                path = GlobalConstants.IMAGE_PATH.concat(name).concat(".jpg");
                FileOutputStream fos = new FileOutputStream(path);
                byte[] b = new byte[1024];
                int length;
                while ((length = is.read(b)) != -1) {
                    fos.write(b, 0, length);
                }
                is.close();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }

    /**
     * 获取用户认证结果
     * @param code
     * @return
     */
    public AuthResponse jscode2session(String code) {
        String domain = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> params = Maps.newHashMap();
        params.put("appid", GlobalConstants.APP_ID);
        params.put("secret", GlobalConstants.APP_SECRET);
        params.put("grant_type", GlobalConstants.GrantType.AUTHORIZATION_CODE.getKey());
        params.put("js_code", code);
        ResponseEntity<String> responseEntity = http.getForEntity(domain, params, String.class);
        AuthResponse authResponse = JSONObject.parseObject(responseEntity.getBody(), AuthResponse.class);
        return authResponse;
    }
}
