package com.mq.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class VideoSecurityConfig implements WebMvcConfigurer {

    @Bean
    public VideoSecurityInterceptor getVideoSecurityInterceptor() {
        return new VideoSecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getVideoSecurityInterceptor());
        addInterceptor.excludePathPatterns("/wx/index/**")
                        .excludePathPatterns("/wx/auth")
                        .excludePathPatterns("/wx/saveUser")
                        .addPathPatterns("/wx/**");
    }

    private class VideoSecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String skey = request.getParameter("skey");
            if (GlobalConstants.USER_CACHE.get(skey) != null) {
                return true;
            }
            response.sendRedirect("/mq/login");
            return false;
        }
    }

}
