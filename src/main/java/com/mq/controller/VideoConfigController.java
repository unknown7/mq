package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.mq.base.BaseController;
import com.mq.model.VideoClassification;
import com.mq.query.VideoClassificationQuery;
import com.mq.service.VideoConfigService;
import com.mq.vo.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/videoConfig")
public class VideoConfigController extends BaseController {
    @Resource
    private VideoConfigService videoConfigService;

    @RequestMapping("/index")
    public String index() {
        return "videoConfig";
    }

    @RequestMapping("/findClassification")
    @ResponseBody
    public String findClassification(HttpServletRequest request, VideoClassificationQuery query) {
        Page<VideoClassification> page = videoConfigService.findClassificationPageByQuery(query);
        return JSON.toJSONString(page);
    }

    @RequestMapping("/saveClassification")
    @ResponseBody
    public String saveClassification(VideoClassification classification) {
        try {
            videoConfigService.saveClassification(classification);
            return success("分类设置已保存");
        } catch (Exception e) {
            e.printStackTrace();
            return error("分类设置保存时出现问题");
        }
    }

    @RequestMapping("/removeClassification")
    @ResponseBody
    public String removeClassification(Long id) {
        try {
            videoConfigService.removeClassification(id);
            return success("分类设置已删除");
        } catch (Exception e) {
            e.printStackTrace();
            return error("分类设置删除时出现问题");
        }
    }

    @RequestMapping("/selectOneClassification")
    @ResponseBody
    public String selectOneClassification(Long id) {
        VideoClassification classification = videoConfigService.selectOneClassificationById(id);
        return JSON.toJSONString(classification);
    }
}
