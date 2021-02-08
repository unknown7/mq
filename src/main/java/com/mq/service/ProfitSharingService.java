package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.query.ProfitSharingQuery;
import com.mq.vo.ProfitSharingVo;

public interface ProfitSharingService {

	void profitShare();

	PageInfo<ProfitSharingVo> findPage(ProfitSharingQuery query);
}
