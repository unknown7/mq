package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.AboutUs;
import com.mq.model.Banner;
import com.mq.model.VideoClassification;
import com.mq.query.BannerQuery;
import com.mq.query.VideoClassificationQuery;
import com.mq.vo.BannerVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BasicConfigService {

    PageInfo<VideoClassification> findClassificationPageByQuery(VideoClassificationQuery query);

    void saveClassification(VideoClassification videoClassification);

    void removeClassification(Long id);

    VideoClassification selectOneClassificationById(Long id);

    List<VideoClassification> findClassification(VideoClassificationQuery query);

    PageInfo<BannerVo> findBannerPage(BannerQuery query);

    void saveBanner(MultipartFile image, String id, String bName, String sort, String jumpTo, String desc) throws IOException;

    void removeBanner(Long id);

    Banner selectOneBannerById(Long id);

    List<Banner> findBanner(BannerQuery query);

    AboutUs getAboutUs();
}
