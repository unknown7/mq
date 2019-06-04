package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mq.base.BaseController;
import com.mq.model.Video;
import com.mq.model.VideoClassification;
import com.mq.query.VideoClassificationQuery;
import com.mq.query.VideoQuery;
import com.mq.service.BasicConfigService;
import com.mq.service.VideoService;
import com.mq.vo.VideoVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {
    @Resource
    private VideoService videoService;
    @Resource
    private BasicConfigService basicConfigService;

    @RequestMapping("/index")
    public String index() {
        return "video";
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public String findPage(VideoQuery query) {
        PageInfo<VideoVo> result = videoService.findPage(query);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/findAllClassification")
    @ResponseBody
    public String findAllClassification() {
        List<VideoClassification> classifications = basicConfigService.findClassification(new VideoClassificationQuery());
        return JSON.toJSONString(classifications);
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(MultipartFile cover, MultipartFile description, HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String subtitle = request.getParameter("subtitle");
            String classification = request.getParameter("classification");
            String price = request.getParameter("price");
            String profitShare = request.getParameter("profitShare");
            String freeWatchTime = request.getParameter("freeWatchTime");
            videoService.save(id, title, subtitle, classification, price, profitShare, freeWatchTime, cover, description);
            return success("视频信息已保存");
        } catch (Exception e) {
            e.printStackTrace();
            return error("视频信息保存时出现问题");
        }
    }

    @RequestMapping("/selectOne")
    @ResponseBody
    public String selectOne(Long id) {
        Video video = videoService.selectOneById(id);
        return JSON.toJSONString(video);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public String remove(Long id) {
        try {
            videoService.remove(id);
            return success("视频已删除");
        } catch (Exception e) {
            e.printStackTrace();
            return error("视频删除时出现问题");
        }
    }

    @RequestMapping("/shelve")
    @ResponseBody
    public String shelve(Long id) {
        try {
            videoService.shelve(id);
            return success("视频已下架");
        } catch (Exception e) {
            e.printStackTrace();
            return error("视频下架时出现问题");
        }
    }

    @RequestMapping("/release")
    @ResponseBody
    public String release(Long id) {
        try {
            videoService.release(id);
            return success("视频已发布");
        } catch (Exception e) {
            e.printStackTrace();
            return error("视频发布时出现问题");
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile video, HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            videoService.upload(id, video);
            return success("视频已上传");
        } catch (Exception e) {
            e.printStackTrace();
            return error("视频上传时出现问题");
        }
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public String findAll() {
        List<VideoVo> result = videoService.findAll();
        return JSON.toJSONString(result);
    }
}
