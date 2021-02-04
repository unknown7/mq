package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.GlobalConstants;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.EmployeeMapper;
import com.mq.mapper.UserMapper;
import com.mq.model.Employee;
import com.mq.model.User;
import com.mq.query.EmployeeQuery;
import com.mq.service.EmployeeService;
import com.mq.util.DateUtil;
import com.mq.util.FileUtil;
import com.mq.util.MD5;
import com.mq.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;
	@Resource
	private RedisObjectHolder redisObjectHolder;
	@Resource
	private UserMapper userMapper;

    @Override
    public PageInfo<Employee> findPage(EmployeeQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeMapper.selectByQuery(query));
        return pageInfo;
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
                     String openId,
                     String profitRate,
                     MultipartFile avatar
    ) throws Exception {
        Employee employee = new Employee();
        this
        .handleEmployee(id, username, password, eName, birth, gender, mobile, email, openId, profitRate, employee)
        .handleAvatar(employee, avatar)
        .executeSave(employee, avatar);
    }

    private EmployeeServiceImpl handleEmployee(String id,
                                               String username,
                                               String password,
                                               String eName,
                                               String birth,
                                               String gender,
                                               String mobile,
                                               String email,
                                               String openId,
											   String profitRate,
                                               Employee employee
    ) throws Exception {
        Date now = new Date();
        employee.setBirth(DateUtil.stringToDate(birth));
        employee.setAge(DateUtil.calcAge(employee.getBirth(), now));
        employee.setUsername(username);
        employee.seteName(eName);
        employee.setGender(Integer.valueOf(gender));
        employee.setMobile(mobile);
        employee.setEmail(email);
        employee.setOpenId(openId);
        employee.setModifiedTime(now);
		employee.setProfitRate(new BigDecimal(profitRate).divide(new BigDecimal("100")));
        if (StringUtils.isEmpty(id)) {
            employee.setPassword(MD5.generate(password));
            employee.setCreatedTime(now);
            employee.setDelFlag(Boolean.FALSE);
        } else {
            employee.setId(Long.valueOf(id));
            Employee byPrimaryKey = employeeMapper.selectByPrimaryKey(employee.getId());
            if (!byPrimaryKey.getPassword().equals(password)) {
                employee.setPassword(MD5.generate(password));
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

    private void executeSave(Employee employee, MultipartFile avatar) throws Exception {
        /**
         * 新增员工
         */
        if (employee.getId() == null) {
            employeeMapper.insertSelective(employee);
            FileUtil.persistFile(avatar, employee.getAvatarRealName(), GlobalConstants.IMAGE_PATH);
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
                FileUtil.removeFile(avatarRealPath);
                FileUtil.persistFile(avatar, employee.getAvatarRealName(), GlobalConstants.IMAGE_PATH);
            }
        }
		/**
		 * 更新用户信息
		 */
        if (!StringUtils.isEmpty(employee.getOpenId())) {
			String skey = MD5.generate(employee.getOpenId());
			User user = userMapper.selectBySkey(skey);
			if (employee.getOpenId().equals(user.getOpenId())) {
				UserVo userInfo = redisObjectHolder.getUserInfo(skey);
				if (userInfo != null) {
					userInfo.setIsEmployee(employee.getOpenId().equals(user.getOpenId()));
					redisObjectHolder.setUserInfo(skey, userInfo);
				}
			}
		}
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

	@Override
	public Map<String, Employee> findAllGroupByOpenId() {
		List<Employee> employees = employeeMapper.selectByQuery(new EmployeeQuery());
		return employees.stream().collect(Collectors.toMap(Employee::getOpenId, v -> v));
	}
}
