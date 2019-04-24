package com.mq.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WxSecurityConfig implements WebMvcConfigurer {

    @Resource
    private RedisObjectHolder redisObjectHolder;

    @Bean
    public VideoSecurityInterceptor getVideoSecurityInterceptor() {
        return new VideoSecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getVideoSecurityInterceptor());
        addInterceptor.excludePathPatterns("/wx/index/**")
                        .excludePathPatterns("/wx/auth")
                        .excludePathPatterns("/wx/saveUser")
                        .excludePathPatterns("/wx/listUserCache")
                        .excludePathPatterns("/wx/clearUserCache")
                        .excludePathPatterns("/wx/video/getVideo")
                        .addPathPatterns("/wx/**");
    }

    private class VideoSecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String skey = request.getParameter("skey");
            if (!StringUtils.isEmpty(skey) && redisObjectHolder.getUserInfo(skey) != null) {
                return true;
            }
            return false;
        }
    }

}
