package com.mq.mapper;

import com.mq.model.ProfitSharing;

public interface ProfitSharingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProfitSharing record);

    int insertSelective(ProfitSharing record);

    ProfitSharing selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProfitSharing record);

    int updateByPrimaryKey(ProfitSharing record);
}