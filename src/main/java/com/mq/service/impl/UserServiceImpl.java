package com.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.ShareCardMapper;
import com.mq.mapper.UserMapper;
import com.mq.model.ShareCard;
import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.service.UserService;
import com.mq.util.MD5;
import com.mq.util.MapUtil;
import com.mq.util.PageUtil;
import com.mq.util.WxDecrptUtil;
import com.mq.vo.Page;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.auth.AuthRequest;
import com.mq.wx.vo.auth.AuthResponse;
import com.mq.wx.vo.auth.AuthResult;
import com.mq.wx.vo.auth.TemporaryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private WxAPI wxAPI;
    @Resource
    private RedisObjectHolder redisObjectHolder;
    @Resource
    private ShareCardMapper shareCardMapper;

    @Override
    @Transactional
    public AuthResult save(AuthRequest request) throws Exception {
        TemporaryUser temporaryUser = redisObjectHolder.getTemporaryUser(request.getSkey());
        String encryptedData = request.getEncryptedData();
        String iv = request.getIv();
        AuthResult authResult = new AuthResult(true);
        User user = WxDecrptUtil.getUserInfo(encryptedData, temporaryUser.getSessionKey(), iv);
        /**
         * 注册用户
         */
        String skey = MD5.generate(temporaryUser.getOpenId());
        Date now = new Date();
        user.setCreateTime(now);
        user.setDelFlag(0);
        user.setSessionKey(temporaryUser.getSessionKey());
        user.setSkey(skey);
        user.setUpdateTime(now);
        /**
         * 推荐人
         */
        Long shareCardId = request.getShareCardId();
        if (shareCardId != null) {
            ShareCard shareCard = shareCardMapper.selectByPrimaryKey(shareCardId);
            Long referrer = shareCard.getUserId();
            user.setReferrer(referrer);
        }
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
        /**
         * 保存用户结果参数
         */
        authResult.setSkey(skey);
        authResult.setUserVo(userVo);
        return authResult;
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
        logger.info("调用auth接口，skey=" +  skey + "，code=" + code);
        AuthResponse authResponse = wxAPI.jscode2session(code);
        logger.info("验证用户，临时登录凭证：" + code + "，微信鉴权结果：" + JSON.toJSONString(authResponse));
        AuthResult authResult = new AuthResult(true);
        /**
         * 1 微信登录成功
         */
        if (!StringUtils.isEmpty(authResponse.getSession_key())) {
            String sessionKey = authResponse.getSession_key();
            String openId = authResponse.getOpenid();
            String openIdMD5 = MD5.generate(authResponse.getOpenid());
            TemporaryUser temporaryUser = new TemporaryUser();
            temporaryUser.setSkey(openIdMD5);
            temporaryUser.setSessionKey(sessionKey);
            temporaryUser.setOpenId(openId);
            logger.info("1 微信登录成功，skey=" + skey);
            /**
             * 1.1 有skey，小程序判断用户操作超时
             */
            if (!StringUtils.isEmpty(skey)) {
                logger.info("1.1 有skey，小程序判断用户操作超时，skey=" + skey);
                /**
                 * 1.1.1 skey与加密后的openid相等，初步认证成功，进一步检测用户缓存
                 */
                if (skey.equals(openIdMD5)) {
                    logger.info("1.1.1 skey与加密后的openid相等，初步认证成功，进一步检测用户缓存，skey=" + skey);
                    UserVo userVo = redisObjectHolder.getUserInfo(openIdMD5);
                    /**
                     * 1.1.1.1 用户已注册
                     */
                    if (userVo != null) {
                        logger.info("1.1.1.1 用户已注册，skey=" + skey);
                        authResult.setSkey(openIdMD5);
                        authResult.setUserVo(userVo);
                    }
                    /**
                     * 1.1.1.2 用户未注册，检测是否为临时用户
                     */
                    else {
                        logger.info("1.1.1.2 用户未注册，检测是否为临时用户，skey=" + skey);
                        boolean isTemporaryUser = redisObjectHolder.isTemporaryUser(skey);
                        /**
                         * 用户是临时用户，说明用户以前登陆过小程序，但是没有授权获取开放信息，验证成功，返回加密后的openid
                         */
                        if (isTemporaryUser) {
                            logger.info("用户是临时用户，说明用户以前登陆过小程序，但是没有授权获取开放信息，验证成功，返回加密后的openid，skey=" + skey);
                            authResult.setSkey(openIdMD5);
                        }
                        /**
                         * 用户不是临时用户，存在缓存出现问题的可能，检查数据库
                         */
                        else {
                            logger.info("用户不是临时用户，存在缓存出现问题的可能，检查数据库，skey=" + skey);
                            User user = userMapper.selectByOpenId(authResponse.getOpenid());
                            /**
                             * 用户已注册，确定缓存出现问题，刷新用户缓存，验证成功，返回加密后的openid与userVo
                             */
                            if (user != null) {
                                logger.info("用户已注册，确定缓存出现问题，刷新用户缓存，验证成功，返回加密后的openid与userVo，skey=" + skey);
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
                                logger.info("用户未注册，小程序存在错误的skey，验证成功，返回加密后的openid，skey=" + skey);
                                authResult.setSkey(openIdMD5);
                                // 加入临时用户缓存
                                redisObjectHolder.setTemporaryUser(openIdMD5, JSON.toJSONString(temporaryUser));
                            }
                        }
                    }
                }
                /**
                 * 1.1.2 skey与加密后的openid不相等，小程序存在错误的skey，验证成功，返回加密后的openid
                 */
                else {
                    logger.info("1.1.2 skey与加密后的openid不相等，小程序存在错误的skey，验证成功，返回加密后的openid，skey=" + skey);
                    authResult.setSkey(openIdMD5);
                    // 加入临时用户缓存
                    redisObjectHolder.setTemporaryUser(openIdMD5, JSON.toJSONString(temporaryUser));
                }
            }
            /**
             * 1.2 没有skey，检测用户是否已注册
             */
            else {
                logger.info("1.2 没有skey，检测用户是否已注册，skey=" + skey);
                UserVo userVo = redisObjectHolder.getUserInfo(openIdMD5);
                /**
                 * 1.2.1 用户已注册，验证成功，返回加密后的openid与userVo
                 */
                if (userVo != null) {
                    logger.info("1.2.1 用户已注册，验证成功，返回加密后的openid与userVo，skey=" + skey);
                    authResult.setSkey(openIdMD5);
                    authResult.setUserVo(userVo);
                }
                /**
                 * 1.2.2 用户未注册，说明用户首次登录小程序，将加密后的openid存入临时用户缓存，验证结束，小程序存入加密后的openid
                 */
                else {
                    logger.info("1.2.2 用户未注册，说明用户首次登录小程序，将加密后的openid存入临时用户缓存，验证结束，小程序存入加密后的openid，skey=" + skey);
                    authResult.setSkey(openIdMD5);
                    // 加入临时用户缓存
                    redisObjectHolder.setTemporaryUser(openIdMD5, JSON.toJSONString(temporaryUser));
                }
            }
        }
        /**
         * 2 微信登录失败
         */
        else {
            logger.info("2 微信登录失败，skey=" + skey);
            authResult.setSuccess(false);
        }
        return authResult;
    }

    @Override
    public UserVo getVoBySkey(String skey) {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        if (userVo == null) {
            User user = userMapper.selectBySkey(skey);
            if (user != null) {
                userVo = new UserVo();
                BeanUtils.copyProperties(user, userVo);
                redisObjectHolder.setUserInfo(skey, userVo);
            }
        }
        return userVo;
    }

    @Override
    public User getBySkey(String skey) {
        return userMapper.selectBySkey(skey);
    }

    @Override
    public PageInfo<UserVo> findPage(UserQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<UserVo> pageInfo = new PageInfo<>(userMapper.selectVoByQuery(query));
        return pageInfo;
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
