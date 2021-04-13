package com.mq.base;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mq.model.Employee;
import com.mq.model.User;
import com.mq.model.WhiteList;
import com.mq.service.EmployeeService;
import com.mq.service.UserService;
import com.mq.service.WhiteListService;
import com.mq.util.SignUtil;
import com.mq.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    protected static final Logger logger = LoggerFactory.getLogger(StartupListener.class);
    @Resource
    private WebApplicationContext context;
    @Resource
    private UserService userService;
    @Resource
    private WhiteListService whiteListService;
    @Resource
    private RedisObjectHolder redisObjectHolder;
    @Resource
	private EmployeeService employeeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /**
         * 静态资源
         */
        initStaticResources();
        /**
         * 用户缓存
         */
        initUserCache();
        /**
         * 白名单
         */
        initWhiteList();

        logger.info("初始化信息完成!");
    }

    private void initStaticResources() {
        logger.info("初始化静态资源..");
        ServletContext servletContext = context.getServletContext();
        String imagesPath = servletContext.getRealPath("images");
        String videoPath = servletContext.getRealPath("videos");
        GlobalConstants.IMAGE_PATH = imagesPath.concat(File.separator);
        GlobalConstants.VIDEO_PATH = videoPath.concat(File.separator);
    }

    private void initUserCache() {
        try {
            logger.info("初始化用户缓存..");
            List<User> users = userService.findAll();
			Map<String, Employee> employees = employeeService.findAllGroupByOpenId();
			Map<String, String> userVos = Maps.newHashMap();
            for (User user : users) {
                String skey = SignUtil.md5(user.getOpenId());
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(user, vo);
				vo.setIsEmployee(Optional.ofNullable(employees.get(user.getOpenId())).map(i -> Boolean.TRUE).orElse(Boolean.FALSE));
				String serializable = JSON.toJSONString(vo);
                userVos.put(skey, serializable);
            }
            redisObjectHolder.setUserInfo(userVos);
        } catch (Exception e) {
            logger.error("初始化用户信息失败！");
            logger.error(e.getMessage());
        }
    }

    private void initWhiteList() {
        logger.info("初始化白名单..");
        List<WhiteList> all = whiteListService.findAll();
        Map<String, String> whiteList = Maps.newHashMap();
        for (WhiteList whiteUser : all) {
            String skey = whiteUser.getSkey();
            String serializable = JSON.toJSONString(whiteUser);
            whiteList.put(skey, serializable);
        }
        redisObjectHolder.setWhiteList(whiteList);
    }
}
