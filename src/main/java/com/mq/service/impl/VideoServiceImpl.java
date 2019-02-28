package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.mq.base.GlobalConstants;
import com.mq.mapper.VideoMapper;
import com.mq.model.Video;
import com.mq.query.VideoQuery;
import com.mq.service.VideoService;
import com.mq.util.PageUtil;
import com.mq.vo.Page;
import com.mq.vo.VideoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper videoMapper;

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
                     MultipartFile cover,
                     MultipartFile description) throws IOException {
        Video video = new Video();
        this
        .handleVideo(id, title, subtitle, classification, price, shareCommission, video)
        .handleImage(video, cover, description)
        .executeSave(video, cover, description);
    }

    private VideoServiceImpl handleVideo(String id,
                                         String title,
                                         String subtitle,
                                         String classification,
                                         String price,
                                         String shareCommission,
                                         Video video) {
        Date now = new Date();
        if (!StringUtils.isEmpty(shareCommission)) {
            video.setShareCommission(new BigDecimal(shareCommission).divide(new BigDecimal("100")));
        }
        video.setTitle(title);
        video.setSubtitle(subtitle);
        video.setClassification(Long.valueOf(classification));
        video.setPrice(new BigDecimal(price));
        if (StringUtils.isEmpty(id)) {
            video.setWatched(0);
            video.setPurchased(0);
            video.setAccessed(0);
            video.setStatus(GlobalConstants.UN_UPLOADED);
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
            persistFile(cover, GlobalConstants.IMAGE_PATH, video.getCoverRealName());
            persistFile(description, GlobalConstants.IMAGE_PATH, video.getDescriptionRealName());
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
                persistFile(cover, GlobalConstants.IMAGE_PATH, video.getCoverRealName());
            }
            /**
             * 更新了视频描述
             */
            if (!StringUtils.isEmpty(video.getDescriptionRealName())) {
                String descriptionRealPath = GlobalConstants.IMAGE_PATH.concat(byPrimaryKey.getDescriptionRealName());
                File delete = new File(descriptionRealPath);
                delete.delete();
                persistFile(description, GlobalConstants.IMAGE_PATH, video.getDescriptionRealName());
            }
        }
    }

    private void persistFile(MultipartFile file, String type, String realName) throws IOException {
        String realPath = type.concat(realName);
        File dest = new File(realPath);
        file.transferTo(dest);
    }

    @Override
    public Video selectOneById(Long id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setDelFlag(1);
        video.setUpdateTime(new Date());
        String coverPath = GlobalConstants.IMAGE_PATH.concat(video.getCoverRealName());
        String descriptionPath = GlobalConstants.IMAGE_PATH.concat(video.getDescriptionRealName());
        File cover = new File(coverPath);
        File description = new File(descriptionPath);
        videoMapper.updateByPrimaryKeySelective(video);
        cover.delete();
        description.delete();
    }

    @Override
    @Transactional
    public void pulloff(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setStatus(GlobalConstants.UN_RELEASED);
        video.setUpdateTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @Transactional
    public void release(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setStatus(GlobalConstants.RELEASED);
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
        byPrimaryKey.setStatus(GlobalConstants.UN_RELEASED);
        videoMapper.updateByPrimaryKeySelective(byPrimaryKey);
        persistFile(video, GlobalConstants.VIDEO_PATH, byPrimaryKey.getVideoRealName());
    }
}