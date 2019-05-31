package com.mq.wx.controller;

import com.alibaba.fastjson.JSON;
import com.mq.model.Banner;
import com.mq.model.VideoClassification;
import com.mq.query.BannerQuery;
import com.mq.query.VideoClassificationQuery;
import com.mq.service.BasicConfigService;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import com.mq.wx.vo.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wx/index")
public class IndexController {
    protected static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private BasicConfigService basicConfigService;
    @Resource
    private VideoService videoService;

    @RequestMapping("/getClassifications")
    public String getClassifications() {
        VideoClassificationQuery query = new VideoClassificationQuery();
        query.setDelFlag(0);
        List<VideoClassification> classifications = basicConfigService.findClassification(query);
        return JSON.toJSONString(classifications);
    }

    @RequestMapping("/getVideos")
    public String getVideos() {
        List<VideoVo> videoVos = videoService.findReleases();
        return JSON.toJSONString(videoVos);
    }

    @RequestMapping("/getBanners")
    public String getBanners() {
        BannerQuery query = new BannerQuery();
        query.setDelFlag(0);
        List<Banner> banners = basicConfigService.findBanner(query);
        return JSON.toJSONString(banners);
    }

    @RequestMapping("/scene")
    public String scene(String scene) {
        logger.info("scene:" + scene);
        return JSON.toJSONString(DefaultResponse.success(scene));
    }
}
