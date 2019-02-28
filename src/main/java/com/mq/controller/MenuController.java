package com.mq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @RequestMapping("/index")
    public String index() {
        return "menu";
    }
}
