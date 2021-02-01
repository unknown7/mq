package com.mq.mapper;

import com.mq.model.EmpMenu;

import java.util.List;

public interface EmpMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EmpMenu record);

    int insertSelective(EmpMenu record);

    EmpMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EmpMenu record);

    int updateByPrimaryKey(EmpMenu record);

	List<Long> selectMenusByEmpId(Long employeeId);

	int deleteByEid(Long eid);

	int insertBatch(List<EmpMenu> list);
}