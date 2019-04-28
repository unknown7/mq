package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import com.mq.wx.vo.DefaultResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/generateMiniProgramCode")
    @ResponseBody
    public String generateMiniProgramCode(String videoId, String skey) {
        String miniProgramCode = videoService.generateMiniProgramCode(videoId, skey);
        DefaultResponse response = DefaultResponse.create(
                !StringUtils.isEmpty(miniProgramCode),
                miniProgramCode
        );
        return JSON.toJSONString(response);
    }

    @RequestMapping("/saveShareCard")
    @ResponseBody
    public String saveShareCard(@RequestParam("shareCard") MultipartFile shareCard, HttpServletRequest request) {
        DefaultResponse response;
        try {
            String skey = request.getParameter("skey");
            String videoId = request.getParameter("videoId");
            String path = videoService.saveShareCard(shareCard, skey, videoId);
            response = DefaultResponse.success(path);
        } catch (Exception e) {
            e.printStackTrace();
            response = DefaultResponse.fail();
        }
        return JSON.toJSONString(response);
    }
}
