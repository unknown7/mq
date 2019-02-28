package com.mq.mapper;

import com.mq.model.VideoClassification;
import com.mq.query.VideoClassificationQuery;

import java.util.List;

public interface VideoClassificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VideoClassification record);

    int insertSelective(VideoClassification record);

    VideoClassification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VideoClassification record);

    int updateByPrimaryKey(VideoClassification record);

    List<VideoClassification> selectByQuery(VideoClassificationQuery query);

    Long selectNums(VideoClassificationQuery query);
}