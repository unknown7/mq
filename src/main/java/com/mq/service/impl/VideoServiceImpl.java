package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.mq.base.GlobalConstants;
import com.mq.mapper.VideoMapper;
import com.mq.model.Video;
import com.mq.query.VideoQuery;
import com.mq.service.VideoService;
import com.mq.util.FileUtil;
import com.mq.util.PageUtil;
import com.mq.vo.Page;
import com.mq.vo.UserVo;
import com.mq.vo.VideoVo;
import com.mq.wx.base.WxAPI;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private WxAPI wxAPI;

    @Override
    public Page<VideoVo> findPage(VideoQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        Page<VideoVo> page = PageUtil.generatePage(
                videoMapper.selectByQuery(query),
                videoMapper.selectNums(query),
                query
        );
        return page;
    }

    @Override
    @Transactional
    public void save(String id,
                     String title,
                     String subtitle,
                     String classification,
                     String price,
                     String shareCommission,
                     String freeWatchTime,
                     MultipartFile cover,
                     MultipartFile description) throws IOException {
        Video video = new Video();
        this
        .handleVideo(id, title, subtitle, classification, price, shareCommission, freeWatchTime, video)
        .handleImage(video, cover, description)
        .executeSave(video, cover, description);
    }

    private VideoServiceImpl handleVideo(String id,
                                         String title,
                                         String subtitle,
                                         String classification,
                                         String price,
                                         String shareCommission,
                                         String freeWatchTime,
                                         Video video) {
        Date now = new Date();
        if (!StringUtils.isEmpty(shareCommission)) {
            video.setShareCommission(new BigDecimal(shareCommission).divide(new BigDecimal("100")));
        }
        if (!StringUtils.isEmpty(freeWatchTime)) {
            video.setFreeWatchTime(Integer.valueOf(freeWatchTime));
        }
        video.setTitle(title);
        video.setSubtitle(subtitle);
        video.setClassification(Long.valueOf(classification));
        video.setPrice(new BigDecimal(price));
        if (StringUtils.isEmpty(id)) {
            video.setStatus(GlobalConstants.VideoStatus.UN_UPLOADED.getKey());
            video.setCreateTime(now);
            video.setUpdateTime(now);
            video.setDelFlag(0);
        } else {
            video.setId(Long.valueOf(id));
            video.setUpdateTime(now);
        }
        return this;
    }

    private VideoServiceImpl handleImage(Video video, MultipartFile cover, MultipartFile description) {
        if (cover != null && !cover.isEmpty()) {
            String coverName = cover.getOriginalFilename();
            video.setCoverName(coverName);
            int pointIndex = coverName.indexOf(".");
            String fileSuffix = coverName.substring(pointIndex);
            video.setCoverRealName(UUID.randomUUID().toString().concat(fileSuffix));
        }
        if (description != null && !description.isEmpty()) {
            String descriptionName = description.getOriginalFilename();
            video.setDescriptionName(descriptionName);
            int pointIndex = descriptionName.indexOf(".");
            String fileSuffix = descriptionName.substring(pointIndex);
            video.setDescriptionRealName(UUID.randomUUID().toString().concat(fileSuffix));
        }
        return this;
    }

    private void executeSave(Video video, MultipartFile cover, MultipartFile description) throws IOException {
        /**
         * 新增视频信息
         */
        if (video.getId() == null) {
            videoMapper.insertSelective(video);
            FileUtil.persistFile(cover, video.getCoverRealName(), GlobalConstants.IMAGE_PATH);
            FileUtil.persistFile(description, video.getDescriptionRealName(), GlobalConstants.IMAGE_PATH);
        }
        /**
         * 更新视频信息
         */
        else {
            Video byPrimaryKey = videoMapper.selectByPrimaryKey(video.getId());
            videoMapper.updateByPrimaryKeySelective(video);
            /**
             * 更新了封面
             */
            if (!StringUtils.isEmpty(video.getCoverRealName())) {
                String coverRealPath = GlobalConstants.IMAGE_PATH.concat(byPrimaryKey.getCoverRealName());
                File delete = new File(coverRealPath);
                delete.delete();
                FileUtil.persistFile(cover, video.getCoverRealName(), GlobalConstants.IMAGE_PATH);
            }
            /**
             * 更新了视频描述
             */
            if (!StringUtils.isEmpty(video.getDescriptionRealName())) {
                String descriptionRealPath = GlobalConstants.IMAGE_PATH.concat(byPrimaryKey.getDescriptionRealName());
                File delete = new File(descriptionRealPath);
                delete.delete();
                FileUtil.persistFile(description, video.getDescriptionRealName(), GlobalConstants.IMAGE_PATH);
            }
        }
    }

    @Override
    public Video selectOneById(Long id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public VideoVo selectOneWithAuthById(Long id, String skey) {
        UserVo userVo = GlobalConstants.USER_CACHE.get(skey);
        VideoVo videoVo = videoMapper.selectOneVoWithAuth(id, userVo != null ? userVo.getId() : null);
        return videoVo;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setDelFlag(1);
        video.setUpdateTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);

        String coverPath = GlobalConstants.IMAGE_PATH.concat(video.getCoverRealName());
        String descriptionPath = GlobalConstants.IMAGE_PATH.concat(video.getDescriptionRealName());
        FileUtil.removeFile(coverPath);
        FileUtil.removeFile(descriptionPath);
    }

    @Override
    @Transactional
    public void pulloff(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setStatus(GlobalConstants.VideoStatus.UN_RELEASED.getKey());
        video.setUpdateTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @Transactional
    public void release(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setStatus(GlobalConstants.VideoStatus.RELEASED.getKey());
        video.setUpdateTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @Transactional
    public void upload(String id, MultipartFile video) throws IOException {
        String videoName = video.getOriginalFilename();
        int pointIndex = videoName.indexOf(".");
        String fileSuffix = videoName.substring(pointIndex);
        Video byPrimaryKey = videoMapper.selectByPrimaryKey(Long.valueOf(id));
        byPrimaryKey.setVideoName(videoName);
        byPrimaryKey.setVideoRealName(UUID.randomUUID().toString().concat(fileSuffix));
        byPrimaryKey.setUpdateTime(new Date());
        byPrimaryKey.setStatus(GlobalConstants.VideoStatus.UN_RELEASED.getKey());
        videoMapper.updateByPrimaryKeySelective(byPrimaryKey);
        FileUtil.persistFile(video, byPrimaryKey.getVideoRealName(), GlobalConstants.VIDEO_PATH);
    }

    @Override
    public List<VideoVo> findReleaseds() {
        VideoQuery query = new VideoQuery();
        query.setDelFlag(0);
        query.setStatus(GlobalConstants.VideoStatus.RELEASED.getKey());
        query.setOrderBy("classification");
        List<VideoVo> videoVos = videoMapper.selectByQuery(query);
        return videoVos;
    }

    @Override
    public void generateWxQrcode(String skey) throws Exception {
        String accessToken = wxAPI.getAccessToken();

    }
}