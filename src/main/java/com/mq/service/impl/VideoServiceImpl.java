package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import com.mq.base.RedisObjectHolder;
import com.mq.ex.BaseBusinessException;
import com.mq.mapper.ShareCardMapper;
import com.mq.mapper.VerifySwitchMapper;
import com.mq.mapper.VideoMapper;
import com.mq.model.ProfitSharingRatioQueryResponse;
import com.mq.model.ShareCard;
import com.mq.model.VerifySwitch;
import com.mq.model.Video;
import com.mq.query.VideoQuery;
import com.mq.service.InvitationRecordService;
import com.mq.service.VideoService;
import com.mq.util.FileUtil;
import com.mq.vo.UserVo;
import com.mq.vo.VideoVo;
import com.mq.wx.base.WxAPI;
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
    private WxAPI wxAPI;
    @Resource
    private RedisObjectHolder redisObjectHolder;
    @Resource
    private ShareCardMapper shareCardMapper;
    @Resource
    private VerifySwitchMapper verifySwitchMapper;
	@Resource
	private InvitationRecordService invitationRecordService;

    @Override
    public PageInfo<VideoVo> findPage(VideoQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<VideoVo> pageInfo = new PageInfo<>(videoMapper.selectByQuery(query));
        return pageInfo;
    }

    @Override
    @Transactional
    public void save(String id,
                     String title,
                     String subtitle,
                     String classification,
                     String price,
                     String profitShare,
                     String profitSale,
                     String freeWatchTime,
                     MultipartFile cover,
                     MultipartFile description) throws Exception {
        Video video = new Video();
        this
        .handleVideo(id, title, subtitle, classification, price, profitShare, profitSale, freeWatchTime, video)
        .handleImage(video, cover, description)
        .executeSave(video, cover, description);
    }

    private VideoServiceImpl handleVideo(String id,
                                         String title,
                                         String subtitle,
                                         String classification,
                                         String price,
                                         String profitShare,
                                         String profitSale,
                                         String freeWatchTime,
                                         Video video) throws Exception {
        Date now = new Date();
        if (!StringUtils.isEmpty(profitShare)) {
            video.setProfitShare(new BigDecimal(profitShare).divide(new BigDecimal("100")));
        }
		if (!StringUtils.isEmpty(profitSale)) {
			ProfitSharingRatioQueryResponse maxRatioResponse = wxAPI.queryMaxRatio();
			if (!maxRatioResponse.success()) {
				throw new BaseBusinessException("查询分账比例失败，请稍后重试");
			}
			BigDecimal profitSaleBigDecimal = new BigDecimal(profitSale).divide(new BigDecimal("100"));
			if (profitSaleBigDecimal.compareTo(maxRatioResponse.getMaxRatio()) > 0) {
				throw new BaseBusinessException("销售提成大于商户设置的分账比例，请调整后重试");
			}
			video.setProfitSale(profitSaleBigDecimal);
		}
        if (!StringUtils.isEmpty(freeWatchTime)) {
            video.setFreeWatchTime(Integer.valueOf(freeWatchTime));
        }
        video.setTitle(title);
        video.setSubtitle(subtitle);
        video.setClassification(Long.valueOf(classification));
        video.setPrice(new BigDecimal(price));
        if (StringUtils.isEmpty(id)) {
            video.setStatus(Enums.VideoStatus.UN_UPLOADED.getKey());
            video.setCreatedTime(now);
            video.setModifiedTime(now);
            video.setDelFlag(Boolean.FALSE);
        } else {
            video.setId(Long.valueOf(id));
            video.setModifiedTime(now);
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
        VideoVo videoVo;
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        if (redisObjectHolder.isWhiteUser(skey)) {
            videoVo = videoMapper.selectOneVoWithWhiteUser(id);
        } else {
            videoVo = videoMapper.selectOneVoWithAuth(id, userVo != null ? userVo.getId() : null);
        }
        return videoVo;
    }

    @Override
    public boolean isPurchased(Long id, String skey) {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        Boolean isPurchased = videoMapper.isPurchased(id, userVo != null ? userVo.getId() : null);
        return isPurchased;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setDelFlag(Boolean.TRUE);
        video.setModifiedTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);

        String coverPath = GlobalConstants.IMAGE_PATH.concat(video.getCoverRealName());
        String descriptionPath = GlobalConstants.IMAGE_PATH.concat(video.getDescriptionRealName());
        FileUtil.removeFile(coverPath);
        FileUtil.removeFile(descriptionPath);
    }

    @Override
    @Transactional
    public void shelve(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setStatus(Enums.VideoStatus.UN_RELEASED.getKey());
        video.setModifiedTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @Transactional
    public void release(Long id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        video.setStatus(Enums.VideoStatus.RELEASED.getKey());
        video.setModifiedTime(new Date());
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
        byPrimaryKey.setModifiedTime(new Date());
        byPrimaryKey.setStatus(Enums.VideoStatus.UN_RELEASED.getKey());
        videoMapper.updateByPrimaryKeySelective(byPrimaryKey);
        FileUtil.persistFile(video, byPrimaryKey.getVideoRealName(), GlobalConstants.VIDEO_PATH);
    }

    @Override
    public List<VideoVo> findReleases() {
        VideoQuery query = new VideoQuery();
        query.setDelFlag(0);
        query.setStatus(Enums.VideoStatus.RELEASED.getKey());
        query.setOrderBy("f_classification");
        List<VideoVo> videoVos = videoMapper.selectByQuery(query);
        return videoVos;
    }

    @Override
    @Transactional
    public String generateMiniProgramCode(String videoId, String skey) {
        String miniProgramCode = null;
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        VideoVo videoVo = videoMapper.selectVoByPrimaryKey(Long.valueOf(videoId));
        if (userVo != null) {
            Date now = new Date();
            ShareCard shareCard = new ShareCard();
            shareCard.setGoodsId(videoVo.getId());
            shareCard.setGoodsType(Enums.PurchaseType.VIDEO.getKey());
            shareCard.setGoodsPrice(videoVo.getPrice());
            shareCard.setProfitShare(videoVo.getProfitShare());
            if (userVo.getIsEmployee()) {
				shareCard.setProfitSale(videoVo.getProfitSale());
			}
            shareCard.setSkey(skey);
            shareCard.setUserId(userVo.getId());
            shareCard.setCreatedTime(now);
            shareCard.setModifiedTime(now);
            shareCard.setDelFlag(Boolean.FALSE);
            shareCardMapper.insertSelective(shareCard);
            String page = "pages/index/index";
            miniProgramCode = wxAPI.getUnlimited(page, shareCard.getId());
        }
        return miniProgramCode;
    }

    @Override
    @Transactional
    public String saveShareCard(MultipartFile file, String skey, String videoId) throws Exception {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        ShareCard shareCard = shareCardMapper.selectOneByUserIdAndGoodsId(userVo.getId(), Long.valueOf(videoId), Enums.PurchaseType.VIDEO.getKey());
        String realName = UUID.randomUUID().toString().concat(".png");
        try {
            FileUtil.persistFile(file, realName, GlobalConstants.IMAGE_PATH);
            shareCard.setShareCardRealName(realName);
            shareCardMapper.updateByPrimaryKeySelective(shareCard);
        } catch (Exception e) {
            FileUtil.removeFile(realName);
            throw e;
        }
        return realName;
    }

    @Override
    public List<VideoVo> findPurchases(String skey) {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        VerifySwitch verifySwitch = verifySwitchMapper.selectByPrimaryKey(1);
        List<VideoVo> purchases;
        if (redisObjectHolder.isWhiteUser(skey) || verifySwitch.getVerifySwitch()) {
            purchases = videoMapper.findPurchasesWithWhiteUser();
        } else {
            purchases = videoMapper.findPurchases(userVo.getId());
        }
        return purchases;
    }

    @Override
    public List<VideoVo> findAll() {
        VideoQuery query = new VideoQuery();
        query.setDelFlag(0);
        List<VideoVo> videoVos = videoMapper.selectByQuery(query);
        return videoVos;
    }

    @Override
    public VideoVo findByShareCardId(Long shareCardId, String skey) {
        ShareCard shareCard = shareCardMapper.selectByPrimaryKey(shareCardId);
        VideoVo videoVo = videoMapper.selectVoByPrimaryKey(shareCard.getGoodsId());
		invitationRecordService.initRecord(shareCardId, skey);
        return videoVo;
    }
}