package com.mq.mapper;

import com.mq.model.ProfitSharing;
import com.mq.query.ProfitSharingQuery;
import com.mq.vo.ProfitSharingVo;

import java.util.List;

public interface ProfitSharingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProfitSharing record);

    int insertSelective(ProfitSharing record);

    ProfitSharing selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProfitSharing record);

    int updateByPrimaryKey(ProfitSharing record);

    List<ProfitSharingVo> selectByQuery(ProfitSharingQuery query);
}