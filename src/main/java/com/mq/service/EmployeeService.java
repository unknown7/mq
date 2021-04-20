package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.Employee;
import com.mq.query.EmployeeQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmployeeService {

    PageInfo<Employee> findPage(EmployeeQuery query);

    void save(String id,
              String username,
              String password,
              String employeeName,
              String birth,
              String gender,
              String mobile,
              String email,
              String openId,
              String profitRate,
              MultipartFile avatar) throws Exception;

    void remove(Long id);

    Employee selectOneById(Long id);

    Map<String, Employee> findAllGroupByOpenId();

    Employee getByOpenId(String openId);

    void passwordModification(Long id, String oldPassword, String newPassword, String newPasswordConfirm) throws Exception;

    void updateAge();
}
