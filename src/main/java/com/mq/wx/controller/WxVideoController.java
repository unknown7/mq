package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import com.mq.wx.vo.DefaultResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
    public String generateWxQrcode(String videoId, String skey) {
        String qrcodePath = videoService.generateWxQrcode(videoId, skey);
        DefaultResponse response = new DefaultResponse(
                !StringUtils.isEmpty(qrcodePath),
                qrcodePath
        );
        return JSON.toJSONString(response);
    }
}
