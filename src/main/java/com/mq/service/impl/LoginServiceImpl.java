package com.mq.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.mq.base.WebSecurityConfig;
import com.mq.mapper.EmployeeMapper;
import com.mq.model.Employee;
import com.mq.service.LoginService;
import com.mq.util.MD5;
import com.mq.util.MD5Util;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public boolean loginAuth(String username, String password, HttpSession session) throws Exception {
        Employee employee = employeeMapper.selectByUsername(username);
        if (employee != null && StringUtils.equals(employee.getPassword(), MD5.generate(password))) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, employee);
            return true;
        }
        return false;
    }
}
