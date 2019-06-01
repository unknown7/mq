package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.service.StatisticsService;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import com.mq.wx.vo.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/wx/video")
public class WxVideoController {
    protected static final Logger logger = LoggerFactory.getLogger(WxVideoController.class);
    @Resource
    private VideoService videoService;
    @Resource
    private StatisticsService statisticsService;

    @RequestMapping("/getVideo")
    public String getVideo(Long id, String skey) {
        VideoVo video = videoService.selectOneWithAuthById(id, skey);
        statisticsService.accessVideo(skey, video.getId());
        return JSON.toJSONString(video);
    }

    @RequestMapping("/isPurchased")
    public String isPurchased(Long id, String skey) {
        boolean isPurchased = videoService.isPurchased(id, skey);
        return JSON.toJSONString(isPurchased);
    }

    @RequestMapping("/generateMiniProgramCode")
    public String generateMiniProgramCode(String videoId, String skey) {
        String miniProgramCode = videoService.generateMiniProgramCode(videoId, skey);
        DefaultResponse response = DefaultResponse.create(
                !StringUtils.isEmpty(miniProgramCode),
                miniProgramCode
        );
        return JSON.toJSONString(response);
    }

    @RequestMapping("/saveShareCard")
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

    @RequestMapping("/findPurchases")
    public String findPurchases(String skey) {
        List<VideoVo> purchases = videoService.findPurchases(skey);
        return JSON.toJSONString(purchases);
    }

    @RequestMapping("/watchVideoStatistics")
    public String watchVideoStatistics(String skey, Long id) {
        DefaultResponse response;
        try {
            statisticsService.watchVideo(skey, id);
            response = DefaultResponse.create(true, null, null);
        } catch (Exception e) {
            logger.error("统计播放量失败！", e);
            response = DefaultResponse.fail();
        }
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getVideoByShareCardId")
    public String getVideoByShareCardId(String skey, Long shareCardId) {
        VideoVo videoVo = videoService.findByShareCardId(shareCardId);
        return JSON.toJSONString(videoVo);
    }
}
