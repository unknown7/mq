package com.mq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mq.base.GlobalConstants;
import com.mq.mapper.UserMapper;
import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.service.UserService;
import com.mq.util.MD5Util;
import com.mq.util.WxDecrptUtil;
import com.mq.vo.UserVo;
import com.mq.wx.vo.AuthRequest;
import com.mq.wx.vo.AuthResult;
import com.mq.wx.vo.AuthResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public String save(AuthRequest request) {
        String code = request.getCode();
        String encryptedData = request.getEncryptedData();
        String iv = request.getIv();
        AuthResponse authResponse = requestAuth(code);
        String skey = null;
        /**
         * 微信鉴权成功
         */
        if (authResponse.getSession_key() != null) {
            User user = WxDecrptUtil.getUserInfo(encryptedData, authResponse.getSession_key(), iv);
            /**
             * 注册用户
             */
            skey = MD5Util.getEncryption(authResponse.getOpenid());
            Date now = new Date();
            user.setCreateTime(now);
            user.setDelFlag(0);
            user.setSessionKey(authResponse.getSession_key());
            user.setSkey(skey);
            user.setUpdateTime(now);
            userMapper.insertSelective(user);
            /**
             * 存入缓存
             */
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            GlobalConstants.USER_CACHE.put(skey, userVo);
        }
        return skey;
    }

    @Override
    public List<User> findAll() {
        UserQuery query = new UserQuery();
        query.setDelFlag(0);
        List<User> users = userMapper.selectByQuery(query);
        return users;
    }

    @Override
    public AuthResult auth(String code) {
        AuthResponse authResponse = requestAuth(code);
        AuthResult authResult = new AuthResult();
        authResult.setSuccess(false);
        /**
         * 微信鉴权成功
         */
        if (authResponse.getSession_key() != null) {
            String openIdMD5 = MD5Util.getEncryption(authResponse.getOpenid());
            UserVo userVo = GlobalConstants.USER_CACHE.get(openIdMD5);
            /**
             * 用户已注册
             */
            if (userVo != null) {
                authResult.setSuccess(true);
                authResult.setUserVo(userVo);
                authResult.setSkey(openIdMD5);
            }
            /**
             * 用户未注册
             */
            else {
            }
        }
        /**
         * 微信鉴权失败
         */
        else {
        }
        return authResult;
    }

    @Override
    public UserVo get(String skey) {
        UserVo userVo = GlobalConstants.USER_CACHE.get(skey);
        return userVo;
    }

    /**
     * 请求微信接口，获取用户认证结果
     * @param code
     * @return
     */
    private AuthResponse requestAuth(String code) {
        StringBuffer uri = new StringBuffer();
        uri.append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=").append(GlobalConstants.appId)
                .append("&secret=").append(GlobalConstants.appSecret)
                .append("&js_code=").append(code)
                .append("&grant_type=authorization_code")
        ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(headers);
        String result = restTemplate.exchange(
                uri.toString(),
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();
        AuthResponse authResponse = JSONObject.parseObject(result, AuthResponse.class);
        return authResponse;
    }
}

/**
 * code             0611zlhY0NZvZV1Fd6hY0M33hY01zlh-
 * encryptedData    3twM7x5DrfRrgzUv7WccYH20uGTNhunrdBaDG0Z/p51BL8N+fHvqubD/cmd2qB7FfZlitSTIRp3TZqT1eAZ2yyh25FYmwm+V9E0IvxuR4scLhxZOaZzxQvhxI/QOa2Q5iUNd9a6vzvxft0KVeUrBdqp3K2UEi+mVIP71w78Y2BvRexr3/3+PxuRDZSiHAB2UssA+vInmZfyRd/L+4+zZxgwBMUCWrL2OkjdWylHywPCeISG9JqJOgP1ReMAeMyvVCm4FRqFSWrha8msAersggucFZl7YFdaY2xM4KUT3P/xZvKDLJTE4Rey4tQlpLaTLaEeG1rv2W++1TCVoIjQkWCxsNUgWvsD18hhN9RHBj3IgGCFnPmMHuELsCncePytwfq2qgflSAowLXLmsMCZj8MuJPdXxvoJ6lyGVCDSdG6wIEd1Q1CWHyxyDYz//CI4U+UU6ivgtp9M+RFx2frjCzQ==
 * iv               E8Vz+grmMuRBScbZOi6qrw==
 * result           {"session_key":"2FqBPbXo29uj\/G1H5eHaLw==","openid":"oTt2W5DiQU-M7w8LD7EskU-9IjXk"}
 *
 * code             071GNxaM17ks681arv9M19okaM1GNxa4
 * encryptedData    +PvP16ZPK1nos8SNHXHt7K/X67n8bqFL7uEH7mn83s5Oe92YnswOZen+nM0SP9LkoMSsQgx2/b0IkM/0IjYzRnNxgN5VcNxNCm0tajDUVMCYgXnv9QcjpuuVuzt3ZIkBvjk50bwTXM+PmbOkeE8qmCwnKtBOr8vnSlTFyVm43CQgGkyN3EzRs6K5Cu4wVkyrs2g9oo16jhCe35xfjHSwSolyVl9MEi/6w23KT87uHqojM5tSIG1QwbaTzMwrpT27Lbr6zJUyePO9jHOhvGCzeOPqB4cDj+8IQxR9FS2Jfu1aLmsWHbJpxyBk7mZclbCYqKgOPgT+aIb5l1i+qvbwwsBicayvNUupDPwsqZnlhOQrpxxM2ESrQgkHpfZk3DEmEgYbHWEl57MMo5Yea5MN1f1mY3r8PILuUQZ55BKJAwWbUHNZhoDZbWANjLrebVymGtwWxfgiqpLiL9LTmNqA4Q==
 * iv               vdKXkyo6QxYLvx2EjeGWMA==
 * result           {"session_key":"6aS72xM2E02is4ANUPkjeA==","openid":"oTt2W5DiQU-M7w8LD7EskU-9IjXk"}
 */
