package com.mq.mapper;

import com.mq.model.ShareCard;

public interface ShareCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShareCard record);

    int insertSelective(ShareCard record);

    ShareCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShareCard record);

    int updateByPrimaryKey(ShareCard record);

    ShareCard selectOneByUserIdAndGoodsId(Long userId, Long goodsId, String goodsType);
}