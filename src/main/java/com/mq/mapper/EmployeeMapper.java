package com.mq.mapper;

import com.mq.model.Employee;
import com.mq.query.EmployeeQuery;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    Employee selectByUsername(String username);

    List<Employee> selectByQuery(EmployeeQuery query);

    Long selectNums(EmployeeQuery query);
}