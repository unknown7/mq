package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.GlobalConstants;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.EmployeeMapper;
import com.mq.model.*;
import com.mq.query.EmployeeQuery;
import com.mq.service.EmployeeService;
import com.mq.util.DateUtil;
import com.mq.util.FileUtil;
import com.mq.util.MD5;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    protected static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Resource
    private EmployeeMapper employeeMapper;
	@Resource
	private RedisObjectHolder redisObjectHolder;
	@Resource
    private WxAPI wxAPI;
	@Resource
    private GlobalConstants globalConstants;

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
        if (employee.getId() != null) {
            Employee byPrimaryKey = employeeMapper.selectByPrimaryKey(employee.getId());
            String skey = MD5.generate(employee.getOpenId());
            UserVo userInfo = redisObjectHolder.getUserInfo(skey);

            if (StringUtils.isEmpty(employee.getOpenId()) && !StringUtils.isEmpty(byPrimaryKey.getOpenId())) {
                // 删除分账用户
                ProfitSharingRemoveReceiverRequest request = new ProfitSharingRemoveReceiverRequest();
                ProfitSharingRemoveReceiverRequest.Receiver receiver = new ProfitSharingRemoveReceiverRequest.Receiver();
                receiver.setAccount(employee.getOpenId());
                receiver.setType("PERSONAL_OPENID");
                request.setReceiver(receiver);
                request.setAppId(globalConstants.getAppId());
                request.setMchId(globalConstants.getMchId());
                request.setNonceStr(MD5.generate(UUID.randomUUID().toString()));
                ProfitSharingRemoveReceiverResponse response = wxAPI.profitSharingRemoveReceiver(request);
                employee.setPaymentShareReceiver(Boolean.FALSE);
                userInfo.setIsEmployee(Boolean.FALSE);
                redisObjectHolder.setUserInfo(skey, userInfo);
            } else if (!StringUtils.isEmpty(employee.getOpenId()) && StringUtils.isEmpty(byPrimaryKey.getOpenId())) {
                // 添加分账用户
                ProfitSharingAddReceiverRequest request = new ProfitSharingAddReceiverRequest();
                ProfitSharingAddReceiverRequest.Receiver receiver = new ProfitSharingAddReceiverRequest.Receiver();
                receiver.setAccount(employee.getOpenId());
                receiver.setName(employee.geteName());
                receiver.setRelation_type("STAFF");
                receiver.setType("PERSONAL_OPENID");
                request.setReceiver(receiver);
                request.setAppId(globalConstants.getAppId());
                request.setMchId(globalConstants.getMchId());
                request.setNonceStr(MD5.generate(UUID.randomUUID().toString()));
                ProfitSharingAddReceiverResponse response = wxAPI.profitSharingAddReceiver(request);
                employee.setPaymentShareReceiver(Boolean.TRUE);
                userInfo.setIsEmployee(Boolean.TRUE);
                redisObjectHolder.setUserInfo(skey, userInfo);
            }

        } else {

            if (!StringUtils.isEmpty(employee.getOpenId())) {
                String skey = MD5.generate(employee.getOpenId());
                UserVo userInfo = redisObjectHolder.getUserInfo(skey);
                // 添加分账用户
                ProfitSharingAddReceiverRequest request = new ProfitSharingAddReceiverRequest();
                ProfitSharingAddReceiverRequest.Receiver receiver = new ProfitSharingAddReceiverRequest.Receiver();
                receiver.setAccount(employee.getOpenId());
                receiver.setName(employee.geteName());
                receiver.setRelation_type("STAFF");
                receiver.setType("PERSONAL_OPENID");
                request.setReceiver(receiver);
                request.setAppId(globalConstants.getAppId());
                request.setMchId(globalConstants.getMchId());
                request.setNonceStr(MD5.generate(UUID.randomUUID().toString()));
                ProfitSharingAddReceiverResponse response = wxAPI.profitSharingAddReceiver(request);
                employee.setPaymentShareReceiver(Boolean.TRUE);
                userInfo.setIsEmployee(Boolean.TRUE);
                redisObjectHolder.setUserInfo(skey, userInfo);
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

	@Override
	public Employee getByOpenId(String openId) {
		return null;
	}
}
