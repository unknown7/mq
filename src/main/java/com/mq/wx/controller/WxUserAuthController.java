package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.base.GlobalConstants;
import com.mq.service.UserService;
import com.mq.vo.UserVo;
import com.mq.wx.vo.AuthResult;
import com.mq.wx.vo.AuthRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/wx")
public class WxUserAuthController {
    @Resource
    private UserService userService;

    @RequestMapping("/auth")
    @ResponseBody
    public String auth(String code, String skey) {
        AuthResult authResult = userService.auth(code, skey);
        return JSON.toJSONString(authResult);
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public String saveUser(AuthRequest request) {
        String skey = userService.save(request);
        return JSON.toJSONString(skey);
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(String skey) {
        UserVo userVo = userService.get(skey);
        return JSON.toJSONString(userVo);
    }

    @RequestMapping("/listUserCache")
    @ResponseBody
    public String listUserCache() {
        Map<String, UserVo> userCache = GlobalConstants.USER_CACHE;
        return JSON.toJSONString(userCache);
    }
    @RequestMapping("/clearUserCache")
    @ResponseBody
    public String clearUserCache() {
        GlobalConstants.USER_CACHE.clear();
        return JSON.toJSONString("success");
    }
}
