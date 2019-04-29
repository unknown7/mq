package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.base.GlobalConstants;
import com.mq.service.UserService;
import com.mq.vo.UserVo;
import com.mq.wx.vo.auth.AuthResult;
import com.mq.wx.vo.auth.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/wx")
public class WxUserAuthController {
    protected static final Logger logger = LoggerFactory.getLogger(WxUserAuthController.class);
    @Resource
    private UserService userService;

    @RequestMapping("/auth")
    @ResponseBody
    public String auth(String code, String skey) {
        try {
            AuthResult authResult = userService.auth(code, skey);
            return JSON.toJSONString(authResult);
        } catch (Exception e) {
            logger.error("用户授权失败！skey=" + skey);
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public String saveUser(AuthRequest request) {
        try {
            String skey = userService.save(request);
            return JSON.toJSONString(skey);
        } catch (Exception e) {
            logger.error("用户保存失败！requeste=" + JSON.toJSONString(request));
            logger.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(String skey) {
        UserVo userVo = userService.get(skey);
        return JSON.toJSONString(userVo);
    }
}
