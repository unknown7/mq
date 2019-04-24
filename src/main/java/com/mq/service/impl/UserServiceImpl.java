package com.mq.service.impl;

import com.mq.base.GlobalConstants;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.UserMapper;
import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.service.UserService;
import com.mq.util.MD5Util;
import com.mq.util.WxDecrptUtil;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.auth.AuthRequest;
import com.mq.wx.vo.auth.AuthResponse;
import com.mq.wx.vo.auth.AuthResult;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private WxAPI wxAPI;
    @Resource
    private RedisObjectHolder redisObjectHolder;

    @Override
    @Transactional
    public String save(AuthRequest request) {
        String code = request.getCode();
        String encryptedData = request.getEncryptedData();
        String iv = request.getIv();
        AuthResponse authResponse = wxAPI.jscode2session(code);
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
            redisObjectHolder.setUserInfo(skey, userVo);
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
    public AuthResult auth(String code, String skey) {
        AuthResponse authResponse = wxAPI.jscode2session(code);
        AuthResult authResult = new AuthResult();
        authResult.setSuccess(false);
        /**
         * 1 微信登录成功
         */
        if (authResponse.getSession_key() != null) {
            String openIdMD5 = MD5Util.getEncryption(authResponse.getOpenid());
            /**
             * 1.1 有skey，小程序判断用户操作超时
             */
            if (!StringUtils.isEmpty(skey)) {
                /**
                 * 1.1.1 skey与加密后的openid相等，初步认证成功，进一步检测用户是否已注册
                 */
                if (skey.equals(openIdMD5)) {
                    UserVo userVo = redisObjectHolder.getUserInfo(openIdMD5);
                    /**
                     * 1.1.1.1 用户已注册
                     */
                    if (userVo != null) {
                        authResult.setSuccess(true);
                        authResult.setUserVo(userVo);
                        authResult.setSkey(openIdMD5);
                    }
                    /**
                     * 1.1.1.2 用户未注册，存在缓存出现问题的可能，检查数据库
                     * -->若用户存在，将用户信息存入缓存，返回成功
                     * -->若用户不存在，有问题的skey，返回失败，弃用此skey，当做未授权用户
                     */
                    else {
                        User user = userMapper.selectByOpenId(authResponse.getOpenid());
                        if (user != null) {
                            // 此时是userVo == null的分支，直接拷贝
                            BeanUtils.copyProperties(user, userVo);
                            redisObjectHolder.setUserInfo(openIdMD5, userVo);
                            authResult.setSuccess(true);
                            authResult.setUserVo(userVo);
                            authResult.setSkey(openIdMD5);
                        } else {
                            authResult.setSuccess(false);
                        }
                    }
                }
                /**
                 * 1.1.2 skey与加密后的openid不相等，有问题的skey，返回失败，弃用此skey，当做未授权用户
                 */
                else {
                    authResult.setSuccess(false);
                }
            }
            /**
             * 1.2 没有skey，检测用户是否已注册
             */
            else {
                UserVo userVo = redisObjectHolder.getUserInfo(openIdMD5);
                /**
                 * 1.2.1 用户已注册
                 */
                if (userVo != null) {
                    authResult.setSuccess(true);
                    authResult.setUserVo(userVo);
                    authResult.setSkey(openIdMD5);
                }
                /**
                 * 1.2.2 用户未注册
                 */
                else {
                    authResult.setSuccess(false);
                }
            }
        }
        /**
         * 2 微信登录失败
         */
        else {
            authResult.setSuccess(false);
        }
        return authResult;
    }

    @Override
    public UserVo get(String skey) {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        return userVo;
    }
}
