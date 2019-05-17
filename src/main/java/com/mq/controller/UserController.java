package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.mq.base.BaseController;
import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.service.UserService;
import com.mq.vo.Page;
import com.mq.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "user";
    }

    @RequestMapping("/find")
    @ResponseBody
    public String find(UserQuery query) {
        Page<UserVo> result = userService.findPage(query);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/selectOne")
    @ResponseBody
    public String selectOne(Long id) {
        User user = userService.getById(id);
        return JSON.toJSONString(user);
    }
}
