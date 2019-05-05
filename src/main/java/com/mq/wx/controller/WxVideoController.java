package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import com.mq.wx.vo.DefaultResponse;
import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected static final Logger logger = LoggerFactory.getLogger(WxVideoController.class);
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
            logger.error("保存分享卡失败！", e);
            response = DefaultResponse.fail();
        }
        return JSON.toJSONString(response);
    }

    @RequestMapping("/purchase")
    @ResponseBody
    public String purchase(String skey, Long videoId, String scene, HttpServletRequest request) {
        DefaultResponse response;
        try {
            String remoteAddr = request.getRemoteAddr();
            logger.info("用户：" + skey + "购买商品：" + videoId + "，请求统一下单，scene=" + scene);
            UnifiedOrderVo unifiedOrderVo = videoService.purchase(skey, videoId, scene, remoteAddr);
            response = DefaultResponse.success(unifiedOrderVo);
        } catch (Exception e) {
            logger.error("支付失败，skey=" + skey, e);
            response = DefaultResponse.fail("支付失败");
        }
        return JSON.toJSONString(response);
    }
}
