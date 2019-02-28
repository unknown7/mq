package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.mq.base.GlobalConstants;
import com.mq.mapper.EmployeeMapper;
import com.mq.model.Employee;
import com.mq.query.EmployeeQuery;
import com.mq.service.EmployeeService;
import com.mq.util.DateUtil;
import com.mq.util.MD5Util;
import com.mq.util.PageUtil;
import com.mq.vo.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Page<Employee> findPageByQuery(EmployeeQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        Page<Employee> page = PageUtil.generatePage(
                employeeMapper.selectByQuery(query),
                employeeMapper.selectNums(query),
                query
        );
        return page;
    }

    @Override
    @Transactional
    public void save(String id,
                     String username,
                     String password,
                     String eName,
                     String birth,
                     String gender,
                     String mobile,
                     String email,
                     String wechat,
                     MultipartFile avatar
    ) throws IOException {
        Employee employee = new Employee();
        this
        .handleEmployee(id, username, password, eName, birth, gender, mobile, email, wechat, employee)
        .handleAvatar(employee, avatar)
        .executeSave(employee, avatar);
    }

    private EmployeeServiceImpl handleEmployee(String id, String username, String password, String eName, String birth, String gender, String mobile, String email, String wechat, Employee employee) {
        Date now = new Date();
        employee.setBirth(DateUtil.stringToDate(birth));
        employee.setAge(DateUtil.calcAge(employee.getBirth(), now));
        employee.setUsername(username);
        employee.seteName(eName);
        employee.setGender(gender);
        employee.setMobile(mobile);
        employee.setEmail(email);
        employee.setWechat(wechat);
        employee.setUpdateTime(now);
        if (StringUtils.isEmpty(id)) {
            employee.setPassword(MD5Util.getEncryption(password));
            employee.setCreateTime(now);
            employee.setDelFlag(0);
        } else {
            employee.setId(Long.valueOf(id));
            Employee byPrimaryKey = employeeMapper.selectByPrimaryKey(employee.getId());
            if (!byPrimaryKey.getPassword().equals(password)) {
                employee.setPassword(MD5Util.getEncryption(password));
            }
        }
        return this;
    }

    private EmployeeServiceImpl handleAvatar(Employee employee, MultipartFile avatar) {
        if (avatar != null && !avatar.isEmpty()) {
            String avatarName = avatar.getOriginalFilename();
            employee.setAvatarName(avatarName);
            int pointIndex =  avatarName.indexOf(".");
            String fileSuffix = avatarName.substring(pointIndex);
            employee.setAvatarRealName(UUID.randomUUID().toString().concat(fileSuffix));
        }
        return this;
    }

    private void executeSave(Employee employee, MultipartFile avatar) throws IOException {
        /**
         * 新增员工
         */
        if (employee.getId() == null) {
            employeeMapper.insertSelective(employee);
            persistAvatar(avatar, employee.getAvatarRealName());
        }
        /**
         * 更新员工信息
         */
        else {
            Employee byPrimaryKey = employeeMapper.selectByPrimaryKey(employee.getId());
            employeeMapper.updateByPrimaryKeySelective(employee);
            /**
             * 更新了头像
             */
            if (!StringUtils.isEmpty(employee.getAvatarRealName())) {
                String avatarRealPath = GlobalConstants.IMAGE_PATH.concat(byPrimaryKey.getAvatarRealName());
                File delete = new File(avatarRealPath);
                delete.delete();
                persistAvatar(avatar, employee.getAvatarRealName());
            }
        }
    }

    private void persistAvatar(MultipartFile avatar, String realName) throws IOException {
        String avatarRealPath = GlobalConstants.IMAGE_PATH.concat(realName);
        File dest = new File(avatarRealPath);
        avatar.transferTo(dest);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        employeeMapper.deleteByPrimaryKey(id);
        File file = new File(GlobalConstants.IMAGE_PATH.concat(employee.getAvatarRealName()));
        file.delete();
    }

    @Override
    public Employee selectOneById(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }
}
