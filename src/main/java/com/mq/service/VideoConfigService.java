package com.mq.service;

import com.mq.model.VideoClassification;
import com.mq.query.VideoClassificationQuery;
import com.mq.vo.Page;

import java.util.List;

public interface VideoConfigService {
    Page<VideoClassification> findClassificationPageByQuery(VideoClassificationQuery query);

    void saveClassification(VideoClassification videoClassification);

    void removeClassification(Long id);

    VideoClassification selectOneClassificationById(Long id);

    List<VideoClassification> findClassification(VideoClassificationQuery query);
}
