package com.mq.service;

import com.mq.model.Employee;
import com.mq.query.EmployeeQuery;
import com.mq.vo.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {

    Page<Employee> findPageByQuery(EmployeeQuery query);

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
