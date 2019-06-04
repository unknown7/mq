package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.service.OrderService;
import com.mq.vo.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping("/index")
    public String index() {
        return "order";
    }

    @RequestMapping("/find")
    @ResponseBody
    public String find(OrderQuery query) {
        PageInfo<Order> result = orderService.findPage(query);
        return JSON.toJSONString(result);
    }
}
