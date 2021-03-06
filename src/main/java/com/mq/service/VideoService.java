package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.Video;
import com.mq.query.VideoQuery;
import com.mq.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    PageInfo<VideoVo> findPage(VideoQuery query);

    void save(String id,
              String title,
              String subtitle,
              String classification,
              String price,
              String profitShare,
              String profitSale,
              String freeWatchTime,
              MultipartFile cover,
              MultipartFile description
    ) throws Exception;

    Video selectOneById(Long id);

    VideoVo selectOneWithAuthById(Long id, String skey);

    boolean isPurchased(Long id, String skey);

    void remove(Long id);

    void shelve(Long id);

    void release(Long id);

    void upload(String id, MultipartFile video) throws IOException;

    List<VideoVo> findReleases();

    String generateMiniProgramCode(String videoId, String skey);

    String saveShareCard(MultipartFile file, String skey, String videoId) throws Exception;

    List<VideoVo> findPurchases(String skey);

    List<VideoVo> findAll();

    VideoVo findByShareCardId(Long shareCardId, String skey);
}
