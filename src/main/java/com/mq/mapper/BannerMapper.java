package com.mq.mapper;

import com.mq.model.Banner;
import com.mq.query.BannerQuery;
import com.mq.vo.BannerVo;

import java.util.List;

public interface BannerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);

    List<Banner> selectByQuery(BannerQuery query);

    List<BannerVo> selectVoByQuery(BannerQuery query);

    Long selectNums(BannerQuery query);
}