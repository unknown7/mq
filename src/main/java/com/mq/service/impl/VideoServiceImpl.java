package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.*;
import com.mq.model.*;
import com.mq.query.UserQuery;
import com.mq.query.VideoQuery;
import com.mq.service.VideoService;
import com.mq.util.*;
import com.mq.vo.Page;
import com.mq.vo.UserVo;
import com.mq.vo.VideoVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.unifiedOrder.UnifiedOrderVo;
import org.springframework.beans.BeanUtils;
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
import java.util.Map;
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
    private UserMapper userMapper;
    @Resource
    private OrderNoGenerator generator;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UnifiedOrderRequestMapper unifiedOrderRequestMapper;
    @Resource
    private UnifiedOrderResponseMapper unifiedOrderResponseMapper;

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
                     String profitShare,
                     String freeWatchTime,
                     MultipartFile cover,
                     MultipartFile description) throws IOException {
        Video video = new Video();
        this
        .handleVideo(id, title, subtitle, classification, price, profitShare, freeWatchTime, video)
        .handleImage(video, cover, description)
        .executeSave(video, cover, description);
    }

    private VideoServiceImpl handleVideo(String id,
                                         String title,
                                         String subtitle,
                                         String classification,
                                         String price,
                                         String profitShare,
                                         String freeWatchTime,
                                         Video video) {
        Date now = new Date();
        if (!StringUtils.isEmpty(profitShare)) {
            video.setProfitShare(new BigDecimal(profitShare).divide(new BigDecimal("100")));
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
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
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
    public void shelve(Long id) {
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
    public List<VideoVo> findReleases() {
        VideoQuery query = new VideoQuery();
        query.setDelFlag(0);
        query.setStatus(GlobalConstants.VideoStatus.RELEASED.getKey());
        query.setOrderBy("classification");
        List<VideoVo> videoVos = videoMapper.selectByQuery(query);
        return videoVos;
    }

    @Override
    @Transactional
    public String generateMiniProgramCode(String videoId, String skey) {
        String miniProgramCode = null;
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        if (userVo != null) {
            Date now = new Date();
            ShareCard shareCard = new ShareCard();
            shareCard.setGoodsId(Long.valueOf(videoId));
            shareCard.setGoodsType(GlobalConstants.PurchaseType.VIDEO.getKey());
            shareCard.setSkey(skey);
            shareCard.setUserId(userVo.getId());
            shareCard.setCreateTime(now);
            shareCard.setUpdateTime(now);
            shareCard.setDelFlag(0);
            shareCardMapper.insertSelective(shareCard);
            String page = "pages/video/index";
            Map<String, Object> scene = Maps.newHashMap();
            scene.put("videoId", videoId);
            scene.put("shareCardId", shareCard.getId());
            miniProgramCode = wxAPI.getUnlimited(page, scene);
        }
        return miniProgramCode;
    }

    @Override
    @Transactional
    public String saveShareCard(MultipartFile file, String skey, String videoId) throws Exception {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        ShareCard shareCard = shareCardMapper.selectOneByUserIdAndGoodsId(userVo.getId(), Long.valueOf(videoId), GlobalConstants.PurchaseType.VIDEO.getKey());
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
    @Transactional
    public UnifiedOrderVo purchase(String skey, Long videoId, String remoteAddr) throws Exception {
        assert !StringUtils.isEmpty(skey);
        assert videoId != null;
        User user = getUser(skey);
        VideoVo videoVo = videoMapper.selectVoByPrimaryKey(Long.valueOf(videoId));
        Date now = new Date();

        Order order = new Order();
        order.setOrderNo(generator.next());
        order.setOrderStatus(GlobalConstants.OrderStatus.UNPAID.getKey());
        order.setGoodsId(videoId);
        order.setGoodsType(GlobalConstants.PurchaseType.VIDEO.getKey());
        order.setGoodsPrice(videoVo.getPrice());
        order.setUserId(user.getId());
        order.setTotalAmount(videoVo.getPrice());
        order.setWxAmount(videoVo.getPrice());
        order.setAccountBalanceAmount(BigDecimal.ZERO);
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setDelFlag(0);
        orderMapper.insertSelective(order);

        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setAppid(GlobalConstants.APP_ID);
        request.setMchId(GlobalConstants.MCH_ID);
        request.setNonceStr(MD5.generate(UUID.randomUUID().toString()));
        request.setNotifyUrl(GlobalConstants.NOTIFY_URL);
        request.setOutTradeNo(order.getOrderNo());
        request.setTradeType(GlobalConstants.TRADE_TYPE);
        request.setBody("木荃孕产-" + videoVo.getClassificationName() + "-" + videoVo.getTitle());
        request.setOpenid(user.getOpenId());
        request.setTotalFee(Integer.valueOf(videoVo.getPrice().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString()));
        request.setSpbillCreateIp(remoteAddr);
        unifiedOrderRequestMapper.insertSelective(request);

        UnifiedOrderResponse unifiedOrderResponse = wxAPI.unifiedOrder(request);
        unifiedOrderResponseMapper.insertSelective(unifiedOrderResponse);

        Map<String, Object> signData = Maps.newLinkedHashMap();
        signData.put("appId", GlobalConstants.APP_ID);
        signData.put("nonceStr", MD5.generate(UUID.randomUUID().toString()));
        signData.put("package", "prepay_id=" + unifiedOrderResponse.getPrepayId());
        signData.put("signType", "MD5");
        signData.put("timeStamp", System.currentTimeMillis() / 1000);
        signData.put("key", GlobalConstants.API_KEY);
        String paySign = MD5.generate(MapUtil.map2param(signData)).toUpperCase();
        UnifiedOrderVo unifiedOrderVo = new UnifiedOrderVo();
        unifiedOrderVo.setNonceStr(signData.get("nonceStr").toString());
        unifiedOrderVo.set_package(signData.get("package").toString());
        unifiedOrderVo.setPaySign(paySign);
        unifiedOrderVo.setSignType(signData.get("signType").toString());
        unifiedOrderVo.setTimeStamp(signData.get("timeStamp").toString());
        return unifiedOrderVo;
    }

    private User getUser(String skey) throws Exception {
        UserVo userVo = redisObjectHolder.getUserInfo(skey);
        if (userVo == null) {
            UserQuery query = new UserQuery();
            query.setSkey(skey);
            query.setDelFlag(0);
            List<User> users = userMapper.selectByQuery(query);
            if (users.size() == 1) {
                User user = users.get(0);
                BeanUtils.copyProperties(user, userVo);
                redisObjectHolder.setUserInfo(skey, userVo);
            } else {
                throw new Exception("用户信息校验失败，请检查网络");
            }
        }
        User user = userMapper.selectByPrimaryKey(userVo.getId());
        return user;
    }
}