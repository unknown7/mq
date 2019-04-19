package com.mq.base;

import com.mq.model.User;
import com.mq.service.UserService;
import com.mq.util.MD5Util;
import com.mq.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private WebApplicationContext context;
    @Resource
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /**
         * 初始化静态资源
         */
        initStaticResources();
        /**
         * 初始化用户缓存
         */
        initUserCache();
    }

    private void initStaticResources() {
        ServletContext servletContext = context.getServletContext();
        String imagesPath = servletContext.getRealPath("images");
        String videoPath = servletContext.getRealPath("videos");
        GlobalConstants.IMAGE_PATH = imagesPath.concat(File.separator);
        GlobalConstants.VIDEO_PATH = videoPath.concat(File.separator);
    }

    private void initUserCache() {
        List<User> users = userService.findAll();
        for (User user : users) {
            String openId = user.getOpenId();
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(user, vo);
            GlobalConstants.USER_CACHE.put(MD5Util.getEncryption(openId), vo);
        }
    }
}
