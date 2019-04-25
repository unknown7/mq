package com.mq.base;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mq.model.User;
import com.mq.service.UserService;
import com.mq.util.MD5Util;
import com.mq.vo.UserVo;
import org.apache.commons.codec.digest.Md5Crypt;
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

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private WebApplicationContext context;
    @Resource
    private UserService userService;
    @Resource
    private RedisObjectHolder redisObjectHolder;

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
        Map<String, String> userVos = Maps.newHashMap();
        for (User user : users) {
            String skey = MD5Util.getEncryption(user.getOpenId());
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(user, vo);
            String serializable = JSON.toJSONString(vo);
            userVos.put(skey, serializable);
        }
        redisObjectHolder.setUserInfo(userVos);
    }
}
