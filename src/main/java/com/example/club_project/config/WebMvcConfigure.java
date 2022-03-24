package com.example.club_project.config;

import com.example.club_project.config.support.ClubSearchOptionArgumentResolver;
import com.example.club_project.config.support.QueryParamArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(queryParamArgumentResolver());
        argumentResolvers.add(clubSearchOptionArgumentResolver());
    }

    @Bean
    public QueryParamArgumentResolver queryParamArgumentResolver() {
        return new QueryParamArgumentResolver();
    }

    @Bean
    public ClubSearchOptionArgumentResolver clubSearchOptionArgumentResolver() {
        return new ClubSearchOptionArgumentResolver();
    }
}
