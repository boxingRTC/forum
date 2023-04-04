package com.esliceu.forum;

import com.esliceu.forum.interceptor.TokenInterceptor;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ForumApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }

    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/getprofile")
                .addPathPatterns("/c/**");
    }
}