package com.mq.mapper;

import com.mq.model.VerifySwitch;

public interface VerifySwitchMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(VerifySwitch record);

	int insertSelective(VerifySwitch record);

	VerifySwitch selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(VerifySwitch record);

	int updateByPrimaryKey(VerifySwitch record);
}