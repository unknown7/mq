package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.GlobalConstants;
import com.mq.ex.BaseBusinessException;
import com.mq.mapper.AboutUsMapper;
import com.mq.mapper.BannerMapper;
import com.mq.mapper.VideoClassificationMapper;
import com.mq.model.AboutUs;
import com.mq.model.Banner;
import com.mq.model.ProfitSharingRatioQueryResponse;
import com.mq.model.VideoClassification;
import com.mq.query.BannerQuery;
import com.mq.query.VideoClassificationQuery;
import com.mq.service.BasicConfigService;
import com.mq.util.FileUtil;
import com.mq.vo.BannerVo;
import com.mq.wx.base.WxAPI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BasicConfigServiceImpl implements BasicConfigService {
    @Resource
    private VideoClassificationMapper videoClassificationMapper;
    @Resource
    private BannerMapper bannerMapper;
    @Resource
	private AboutUsMapper aboutUsMapper;
	@Resource
	private WxAPI wxAPI;

    @Override
    public PageInfo<VideoClassification> findClassificationPageByQuery(VideoClassificationQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<VideoClassification> pageInfo = new PageInfo<>(videoClassificationMapper.selectByQuery(query));
        return pageInfo;
    }

    @Override
    @Transactional
    public void saveClassification(VideoClassification videoClassification) throws Exception {
        Date now = new Date();
        if (videoClassification.getDefaultProfitShare() != null) {
            videoClassification.setDefaultProfitShare(
                    videoClassification.getDefaultProfitShare().divide(
                            new BigDecimal("100")
                    )
            );
        }
		if (videoClassification.getDefaultProfitSale() != null) {
			ProfitSharingRatioQueryResponse maxRatioResponse = wxAPI.queryMaxRatio();
			if (!maxRatioResponse.success()) {
				throw new BaseBusinessException("查询分账比例失败，请稍后重试");
			}
			BigDecimal profitSale = videoClassification.getDefaultProfitSale().divide(new BigDecimal("100"));
			if (profitSale.compareTo(maxRatioResponse.getMaxRatio()) > 0) {
				throw new BaseBusinessException("默认分账比例大于商户设置的分账比例，请调整后重试");
			}
			videoClassification.setDefaultProfitSale(profitSale);
		}
        /**
         * 新增
         */
        if (videoClassification.getId() == null) {
            videoClassification.setCreatedTime(now);
            videoClassification.setModifiedTime(now);
            videoClassification.setDelFlag(Boolean.FALSE);
            videoClassificationMapper.insertSelective(videoClassification);
        }
        /**
         * 修改
         */
        else {
            videoClassification.setModifiedTime(now);
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

    @Override
    public PageInfo<BannerVo> findBannerPage(BannerQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<BannerVo> pageInfo = new PageInfo<>(bannerMapper.selectVoByQuery(query));
        return pageInfo;
    }

    @Override
    @Transactional
    public void saveBanner(MultipartFile image,
                           String id,
                           String bName,
                           String sort,
                           String jumpTo,
                           String desc
    ) throws IOException {
        Banner banner = new Banner();
        this.handleBanner(id, bName, sort, jumpTo, desc, banner)
                .handleImage(banner, image)
                .executeSave(banner, image)
        ;
    }

    private BasicConfigServiceImpl handleBanner(String id, String bName, String sort, String jumpTo, String desc, Banner banner) {
        Date now = new Date();
        banner.setbName(bName);
        banner.setDesc(desc);
        banner.setJumpTo(StringUtils.isEmpty(jumpTo) ? null : Long.valueOf(jumpTo));
        banner.setSort(Integer.valueOf(sort));
        banner.setModifiedTime(now);
        if (StringUtils.isEmpty(id)) {
            banner.setCreatedTime(now);
            banner.setDelFlag(Boolean.FALSE);
        } else {
            banner.setId(Long.valueOf(id));
        }
        return this;
    }

    private BasicConfigServiceImpl handleImage(Banner banner, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            banner.setImageName(imageName);
            int pointIndex =  imageName.indexOf(".");
            String fileSuffix = imageName.substring(pointIndex);
            banner.setImageRealName(UUID.randomUUID().toString().concat(fileSuffix));
        }
        return this;
    }

    private void executeSave(Banner banner, MultipartFile image) throws IOException {
        /**
         * 新增轮播
         */
        if (banner.getId() == null) {
            bannerMapper.insertSelective(banner);
            FileUtil.persistFile(image, banner.getImageRealName(), GlobalConstants.IMAGE_PATH);
        }
        /**
         * 更新轮播信息
         */
        else {
            Banner byPrimaryKey = bannerMapper.selectByPrimaryKey(banner.getId());
            bannerMapper.updateByPrimaryKeySelective(banner);
            /**
             * 更新了图片
             */
            if (!StringUtils.isEmpty(banner.getImageRealName())) {
                String imageRealPath = GlobalConstants.IMAGE_PATH.concat(byPrimaryKey.getImageRealName());
                FileUtil.removeFile(imageRealPath);
                FileUtil.persistFile(image, banner.getImageRealName(), GlobalConstants.IMAGE_PATH);
            }
        }
    }

    @Override
    @Transactional
    public void removeBanner(Long id) {
        Banner byPrimaryKey = bannerMapper.selectByPrimaryKey(id);
        bannerMapper.deleteByPrimaryKey(id);
        String imageRealPath = GlobalConstants.IMAGE_PATH.concat(byPrimaryKey.getImageRealName());
        FileUtil.removeFile(imageRealPath);
    }

    @Override
    public Banner selectOneBannerById(Long id) {
        return bannerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Banner> findBanner(BannerQuery query) {
        List<Banner> banners = bannerMapper.selectByQuery(query);
        return banners;
    }

	@Override
	public AboutUs getAboutUs() {
		return aboutUsMapper.selectOne();
	}
}
