package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.mq.mapper.VideoClassificationMapper;
import com.mq.model.VideoClassification;
import com.mq.query.VideoClassificationQuery;
import com.mq.service.VideoConfigService;
import com.mq.util.PageUtil;
import com.mq.vo.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class VideoConfigServiceImpl implements VideoConfigService {
    @Resource
    private VideoClassificationMapper videoClassificationMapper;

    @Override
    public Page<VideoClassification> findClassificationPageByQuery(VideoClassificationQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        Page<VideoClassification> page = PageUtil.generatePage(
                videoClassificationMapper.selectByQuery(query),
                videoClassificationMapper.selectNums(query),
                query
        );
        return page;
    }

    @Override
    @Transactional
    public void saveClassification(VideoClassification videoClassification) {
        Date now = new Date();
        if (videoClassification.getDefaultShareCommission() != null) {
            videoClassification.setDefaultShareCommission(videoClassification.getDefaultShareCommission() / 100);
        }
        /**
         * 新增
         */
        if (videoClassification.getId() == null) {
            videoClassification.setCreateTime(now);
            videoClassification.setUpdateTime(now);
            videoClassification.setDelFlag(0);
            videoClassificationMapper.insertSelective(videoClassification);
        }
        /**
         * 修改
         */
        else {
            videoClassification.setUpdateTime(now);
            videoClassificationMapper.updateByPrimaryKeySelective(videoClassification);
        }
    }

    @Override
    @Transactional
    public void removeClassification(Long id) {
        videoClassificationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public VideoClassification selectOneClassificationById(Long id) {
        return videoClassificationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<VideoClassification> findClassification(VideoClassificationQuery query) {
        List<VideoClassification> videoClassifications = videoClassificationMapper.selectByQuery(query);
        return videoClassifications;
    }
}
