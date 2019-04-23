package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/wx/video")
public class WxVideoController {
    @Resource
    private VideoService videoService;

    @RequestMapping("/getVideo")
    @ResponseBody
    public String getVideo(Long id, String skey) {
        VideoVo video = videoService.selectOneWithAuthById(id, skey);
        return JSON.toJSONString(video);
    }

    @RequestMapping("/generateWxQrcode")
    @ResponseBody
    public String generateWxQrcode(String skey) {

        return null;
    }
}
