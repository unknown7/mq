package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mq.base.BaseController;
import com.mq.model.Banner;
import com.mq.model.VideoClassification;
import com.mq.query.BannerQuery;
import com.mq.query.VideoClassificationQuery;
import com.mq.service.BasicConfigService;
import com.mq.vo.BannerVo;
import com.mq.vo.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/basicConfig")
public class BasicConfigController extends BaseController {
    @Resource
    private BasicConfigService basicConfigService;

    @RequestMapping("/index")
    public String index() {
        return "basicConfig/basicConfig";
    }

    @RequestMapping("/findClassification")
    @ResponseBody
    public String findClassification(HttpServletRequest request, VideoClassificationQuery query) {
        PageInfo<VideoClassification> page = basicConfigService.findClassificationPageByQuery(query);
        return JSON.toJSONString(page);
    }

    @RequestMapping("/saveClassification")
    @ResponseBody
    public String saveClassification(VideoClassification classification) {
        try {
            basicConfigService.saveClassification(classification);
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
            basicConfigService.removeClassification(id);
            return success("分类设置已删除");
        } catch (Exception e) {
            e.printStackTrace();
            return error("分类设置删除时出现问题");
        }
    }

    @RequestMapping("/selectOneClassification")
    @ResponseBody
    public String selectOneClassification(Long id) {
        VideoClassification classification = basicConfigService.selectOneClassificationById(id);
        return JSON.toJSONString(classification);
    }

    @RequestMapping("/findBannerPage")
    @ResponseBody
    public String findBannerPage(HttpServletRequest request, BannerQuery query) {
        PageInfo<BannerVo> page = basicConfigService.findBannerPage(query);
        return JSON.toJSONString(page);
    }

    @RequestMapping("/saveBanner")
    @ResponseBody
    public String saveBanner(@RequestParam("image") MultipartFile image, HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String bName = request.getParameter("bName");
            String sort = request.getParameter("sort");
            String jumpTo = request.getParameter("jumpTo");
            String desc = request.getParameter("desc");
            basicConfigService.saveBanner(image, id, bName, sort, jumpTo, desc);
            return success("轮播图设置已保存");
        } catch (Exception e) {
            e.printStackTrace();
            return error("轮播图设置保存时出现问题");
        }
    }

    @RequestMapping("/removeBanner")
    @ResponseBody
    public String removeBanner(Long id) {
        try {
            basicConfigService.removeBanner(id);
            return success("轮播图设置已删除");
        } catch (Exception e) {
            e.printStackTrace();
            return error("轮播图设置删除时出现问题");
        }
    }

    @RequestMapping("/selectOneBanner")
    @ResponseBody
    public String selectOneBanner(Long id) {
        Banner banner = basicConfigService.selectOneBannerById(id);
        return JSON.toJSONString(banner);
    }
}
