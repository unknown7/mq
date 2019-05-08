package com.mq.mapper;

import com.mq.model.Video;
import com.mq.query.VideoQuery;
import com.mq.vo.VideoVo;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<VideoVo> selectByQuery(VideoQuery query);

    Long selectNums(VideoQuery query);

    VideoVo selectVoByPrimaryKey(Long id);

    VideoVo selectOneVoWithAuth(Long videoId, Long userId);

    List<VideoVo> findPurchases(Long userId);
}