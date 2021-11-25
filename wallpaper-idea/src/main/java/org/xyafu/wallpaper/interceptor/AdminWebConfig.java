package org.xyafu.wallpaper.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OriginSessionInterceptor())//拦截器注册到容器中
                .addPathPatterns("/**") ; //所有请求都被拦截包括静态资源
//                .excludePathPatterns("/", "/login", "/css/**", "/fonts/**", "/images/**",
//                        "/js/**", "/aa/**"); //放行的请求
    }
}
