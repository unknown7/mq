package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.mq.base.GlobalConstants;
import com.mq.model.Banner;
import com.mq.model.Video;
import com.mq.model.VideoClassification;
import com.mq.query.BannerQuery;
import com.mq.query.VideoClassificationQuery;
import com.mq.query.VideoQuery;
import com.mq.service.BasicConfigService;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/wx/index")
public class IndexController {
    @Resource
    private BasicConfigService basicConfigService;
    @Resource
    private VideoService videoService;

    @RequestMapping("/getClassifications")
    @ResponseBody
    public String getClassifications() {
        VideoClassificationQuery query = new VideoClassificationQuery();
        query.setDelFlag(0);
        List<VideoClassification> classifications = basicConfigService.findClassification(query);
        return JSON.toJSONString(classifications);
    }

    @RequestMapping("/getVideos")
    @ResponseBody
    public String getVideos(Long id) {
        VideoQuery query = new VideoQuery();
        query.setDelFlag(0);
        query.setClassification(id);
        query.setStatus(GlobalConstants.VideoStatus.RELEASED.getKey());
        List<VideoVo> videos = videoService.find(query);
        return JSON.toJSONString(videos);
    }

    @RequestMapping("/getBanners")
    @ResponseBody
    public String getBanners() {
        BannerQuery query = new BannerQuery();
        query.setDelFlag(0);
        List<Banner> banners = basicConfigService.findBanner(query);
        return JSON.toJSONString(banners);
    }

    @RequestMapping("/getVideo")
    @ResponseBody
    public String getVideo(Long id) {
        Video video = videoService.selectOneById(id);
        return JSON.toJSONString(video);
    }
}
