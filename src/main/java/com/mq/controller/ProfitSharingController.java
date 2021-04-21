package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mq.base.BaseController;
import com.mq.query.ManualProfitSharingQuery;
import com.mq.query.ProfitSharingQuery;
import com.mq.service.ProfitSharingService;
import com.mq.util.DateUtil;
import com.mq.vo.ProfitSharingVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sharing")
public class ProfitSharingController extends BaseController {

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

	@PostMapping("/exec")
	@ResponseBody
	public String exec(@RequestBody ManualProfitSharingQuery query) {
    	if ("xiaoxiao900529".equals(query.getKey())) {
			profitSharingService.profitShare(
					DateUtil.stringToDate(query.getBeginTime(),DateUtil.DATE_TIME_FORMAT),
					DateUtil.stringToDate(query.getEndTime(), DateUtil.DATE_TIME_FORMAT)
			);
		}
		return success();
	}
}
