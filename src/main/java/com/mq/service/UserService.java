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
     * @param code              用户登录临时凭证，由小程序下发
     * @return
     */
    AuthResult auth(String code);

    /**
     * 通过openid获取用户信息
     *
     * @param skey              加密的openid，由小程序缓存提供
     * @return
     */
    UserVo get(String skey);
}
