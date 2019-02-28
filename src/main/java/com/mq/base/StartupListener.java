package com.mq.base;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private WebApplicationContext context;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ServletContext servletContext = context.getServletContext();
        String imagesPath = servletContext.getRealPath("images");
        String videoPath = servletContext.getRealPath("videos");
        GlobalConstants.IMAGE_PATH = imagesPath.concat(File.separator);
        GlobalConstants.VIDEO_PATH = videoPath.concat(File.separator);
    }
}
