package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.Employee;
import com.mq.query.EmployeeQuery;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {

    PageInfo<Employee> findPage(EmployeeQuery query);

    void save(String id,
              String username,
              String password,
              String eName,
              String birth,
              String gender,
              String mobile,
              String email,
              String wechat,
              MultipartFile avatar) throws Exception;

    void remove(Long id);

    Employee selectOneById(Long id);
}
