package com.mq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @RequestMapping("/index")
    public String index() {
        return "banner";
    }
}
