package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.mq.base.BaseController;
import com.mq.base.WebSecurityConfig;
import com.mq.service.LoginService;
import com.mq.service.MenuService;
import com.mq.vo.MenuTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private LoginService loginService;
    @Resource
    private MenuService menuService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect: login";
    }

    @RequestMapping("/loginAuth")
    @ResponseBody
    public String loginAuth(String username, String password, HttpSession session) {
        try {
            if (loginService.loginAuth(username, password, session)) {
                return success();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return error("请输入正确的用户名或密码");
    }

    @RequestMapping("/selectMenuTree")
    @ResponseBody
    public String selectMenuTree(HttpSession session) {
        List<MenuTree> menuTree = menuService.selectMenuTree(currentEmployee(session).getId());
        return JSON.toJSONString(menuTree);
    }
}
