package com.mq.service.impl;

import com.mq.base.RedisObjectHolder;
import com.mq.mapper.UserMapper;
import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.service.UserService;
import com.mq.util.MD5;
import com.mq.util.MD5Util;
import com.mq.util.WxDecrptUtil;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.auth.AuthRequest;
import com.mq.wx.vo.auth.AuthResponse;
import com.mq.wx.vo.auth.AuthResult;
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
    public String save(AuthRequest request) throws Exception {
        String code = request.getCode();
        String encryptedData = request.getEncryptedData();
        String iv = request.getIv();
        AuthResponse authResponse = wxAPI.jscode2session(code);
        String skey = null;
        /**
         * 微信鉴权成功
         */
        if (!StringUtils.isEmpty(authResponse.getSession_key())) {
            User user = WxDecrptUtil.getUserInfo(encryptedData, authResponse.getSession_key(), iv);
            /**
             * 注册用户
             */
            skey = MD5.generate(authResponse.getOpenid());
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
            /**
             * 删除临时用户缓存
             */
            redisObjectHolder.delTemporaryUser(skey);
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
    public AuthResult auth(String code, String skey) throws Exception {
        AuthResponse authResponse = wxAPI.jscode2session(code);
        AuthResult authResult = new AuthResult(true);
        /**
         * 1 微信登录成功
         */
        if (!StringUtils.isEmpty(authResponse.getSession_key())) {
            String openIdMD5 = MD5.generate(authResponse.getOpenid());
            /**
             * 1.1 有skey，小程序判断用户操作超时
             */
            if (!StringUtils.isEmpty(skey)) {
                /**
                 * 1.1.1 skey与加密后的openid相等，初步认证成功，进一步检测用户缓存
                 */
                if (skey.equals(openIdMD5)) {
                    UserVo userVo = redisObjectHolder.getUserInfo(openIdMD5);
                    /**
                     * 1.1.1.1 用户已注册
                     */
                    if (userVo != null) {
                        authResult.setSkey(openIdMD5);
                        authResult.setUserVo(userVo);
                    }
                    /**
                     * 1.1.1.2 用户未注册，检测是否为临时用户
                     */
                    else {
                        boolean isTemporaryUser = redisObjectHolder.isTemporaryUser(skey);
                        /**
                         * 用户是临时用户，说明用户以前登陆过小程序，但是没有授权获取开放信息，验证成功，返回加密后的openid
                         */
                        if (isTemporaryUser) {
                            authResult.setSkey(openIdMD5);
                        }
                        /**
                         * 用户不是临时用户，存在缓存出现问题的可能，检查数据库
                         */
                        else {
                            User user = userMapper.selectByOpenId(authResponse.getOpenid());
                            /**
                             * 用户已注册，确定缓存出现问题，刷新用户缓存，验证成功，返回加密后的openid与userVo
                             */
                            if (user != null) {
                                // 此时是userVo == null的分支，直接拷贝
                                BeanUtils.copyProperties(user, userVo);
                                redisObjectHolder.setUserInfo(openIdMD5, userVo);
                                authResult.setSkey(openIdMD5);
                                authResult.setUserVo(userVo);
                            }
                            /**
                             * 用户未注册，小程序存在错误的skey，验证成功，返回加密后的openid
                             */
                            else {
                                authResult.setSkey(openIdMD5);
                                // 加入临时用户缓存
                                redisObjectHolder.setTemporaryUser(openIdMD5);
                            }
                        }
                    }
                }
                /**
                 * 1.1.2 skey与加密后的openid不相等，小程序存在错误的skey，验证成功，返回加密后的openid
                 */
                else {
                    authResult.setSkey(openIdMD5);
                    // 加入临时用户缓存
                    redisObjectHolder.setTemporaryUser(openIdMD5);
                }
            }
            /**
             * 1.2 没有skey，检测用户是否已注册
             */
            else {
                UserVo userVo = redisObjectHolder.getUserInfo(openIdMD5);
                /**
                 * 1.2.1 用户已注册，验证成功，返回加密后的openid与userVo
                 */
                if (userVo != null) {
                    authResult.setSkey(openIdMD5);
                    authResult.setUserVo(userVo);
                }
                /**
                 * 1.2.2 用户未注册，说明用户首次登录小程序，将加密后的openid存入临时用户缓存，验证结束，小程序存入加密后的openid
                 */
                else {
                    authResult.setSkey(openIdMD5);
                    // 加入临时用户缓存
                    redisObjectHolder.setTemporaryUser(openIdMD5);
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
