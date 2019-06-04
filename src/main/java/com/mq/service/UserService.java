package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.vo.Page;
import com.mq.vo.UserVo;
import com.mq.wx.vo.auth.AuthRequest;
import com.mq.wx.vo.auth.AuthResult;

import java.util.List;

public interface UserService {
    /**
     * 用户注册
     *
     * @param request->code              用户登录临时凭证，由小程序下发
     * @param request->encryptedData     用户敏感信息（加密数据）
     * @param request->iv                解密向量
     * @return AuthResult                生成的服务器skey与userVo
     */
    AuthResult save(AuthRequest request) throws Exception;

    List<User> findAll();

    /**
     * 调用微信接口获取session_key与openid，并通过openid校验用户是否已注册
     *
     * 校验流程（第一位数字为-的个数，-的个数相等说明在同一层级）：
     * 1-1 微信登录成功
     * 2--1.1 有skey，小程序判断用户操作超时
     * 3---1.1.1 skey与加密后的openid相等，初步认证成功，进一步检测用户缓存
     * 4----1.1.1.1 用户已注册，验证成功，返回加密后的openid与userVo
     * 4----1.1.1.2 用户未注册，检测是否为临时用户
     * 5-----1.1.1.2.1 用户是临时用户，说明用户以前登陆过小程序，但是没有授权获取开放信息，验证成功，返回加密后的openid
     * 5-----1.1.1.2.2 用户不是临时用户，存在缓存出现问题的可能，检查数据库
     * 6------1.1.1.2.2.1 用户已注册，确定缓存出现问题，刷新用户缓存，验证成功，返回加密后的openid与userVo
     * 6------1.1.1.2.2.2 用户未注册，小程序存在错误的skey，验证成功，返回加密后的openid--------------------------------<已微信返回的openid为准>
     * 3---1.1.2 skey与加密后的openid不相等，小程序存在错误的skey，验证成功，返回加密后的openid--------------------------<已微信返回的openid为准>
     * 2--1.2 没有skey，检测用户是否已注册
     * 3---1.2.1 用户已注册，验证成功，返回加密后的openid与userVo
     * 3---1.2.2 用户未注册，说明用户首次登录小程序，将加密后的openid存入临时用户缓存，验证结束，小程序存入加密后的openid
     * 1-2 微信登录失败
     *
     * @param code              用户登录临时凭证，由小程序下发
     * @param skey              用户openid，用于检测当前拥有skey的小程序用户是否为已注册用户
     * @return
     */
    AuthResult auth(String code, String skey) throws Exception;

    /**
     * 通过openid获取用户信息
     *
     * @param skey              加密的openid，由小程序缓存提供
     * @return
     */
    UserVo getVoBySkey(String skey);

    User getBySkey(String skey);

    PageInfo<UserVo> findPage(UserQuery query);

    User getById(Long id);
}
