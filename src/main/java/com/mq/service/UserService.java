package com.mq.service;

import com.mq.model.User;
import com.mq.vo.UserVo;
import com.mq.wx.vo.AuthRequest;
import com.mq.wx.vo.AuthResult;

import java.util.List;

public interface UserService {
    /**
     * 用户注册
     *
     * @param request->code              用户登录临时凭证，由小程序下发
     * @param request->encryptedData     用户敏感信息（加密数据）
     * @param request->iv                解密向量
     * @return skey                      生成的服务器skey
     */
    String save(AuthRequest request);

    List<User> findAll();

    /**
     * 调用微信接口获取session_key与openid，并通过openid校验用户是否已注册
     *
     * 校验流程（第一位数字为-的个数，-的个数相等说明在同一层级）：
     * 1-1 微信登录成功
     * 2--1.1 有skey，小程序判断用户操作超时
     * 3---1.1.1 skey与加密后的openid相等，初步认证成功，进一步检测用户是否已注册
     * 4----1.1.1.1 用户已注册
     * 4----1.1.1.2 用户未注册，存在缓存出现问题的可能，检查数据库
     * 3---1.1.2 skey与加密后的openid不相等，有问题的skey，返回失败，弃用此skey，当做未授权用户
     * 2--1.2 没有skey，检测用户是否已注册
     * 3---1.2.1 用户已注册
     * 3---1.2.2 用户未注册
     * 1-2 微信登录失败
     *
     * @param code              用户登录临时凭证，由小程序下发
     * @param skey              用户openid，用于检测当前拥有skey的小程序用户是否为已注册用户
     * @return
     */
    AuthResult auth(String code, String skey);

    /**
     * 通过openid获取用户信息
     *
     * @param skey              加密的openid，由小程序缓存提供
     * @return
     */
    UserVo get(String skey);
}
