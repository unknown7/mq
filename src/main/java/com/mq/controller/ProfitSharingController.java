package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mq.query.ProfitSharingQuery;
import com.mq.service.ProfitSharingService;
import com.mq.vo.ProfitSharingVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sharing")
public class ProfitSharingController {

    @Resource
    private ProfitSharingService profitSharingService;

    @RequestMapping("/index")
    public String index() {
        return "sharing";
    }

    @RequestMapping("/find")
    @ResponseBody
    public String find(ProfitSharingQuery query) {
        PageInfo<ProfitSharingVo> result = profitSharingService.findPage(query);
        return JSON.toJSONString(result);
    }
}
