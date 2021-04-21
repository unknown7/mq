package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.query.ProfitSharingQuery;
import com.mq.vo.ProfitSharingVo;

import java.util.Date;

public interface ProfitSharingService {

	void profitShare(Date beginTime, Date endTime);

	PageInfo<ProfitSharingVo> findPage(ProfitSharingQuery query);
}
