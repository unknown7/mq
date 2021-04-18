package com.mq.mapper;

import com.mq.model.AboutUs;

public interface AboutUsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AboutUs record);

    int insertSelective(AboutUs record);

    AboutUs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AboutUs record);

    int updateByPrimaryKeyWithBLOBs(AboutUs record);

    AboutUs selectOne();
}