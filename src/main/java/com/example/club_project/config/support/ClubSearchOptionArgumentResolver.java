package com.example.club_project.config.support;

import com.example.club_project.controller.club.ClubDTO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.apache.commons.lang3.StringUtils.*;

public class ClubSearchOptionArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String CLUB_NAME_PARAMETER = "name";
    public static final String CATEGORY_PARAMETER = "category";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ClubDTO.SearchOption.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String clubNameParam = defaultIfEmpty(webRequest.getParameter(CLUB_NAME_PARAMETER), "");
        String categoryParam = defaultIfEmpty(webRequest.getParameter(CATEGORY_PARAMETER), "");

        return ClubDTO.SearchOption.of(clubNameParam, categoryParam);
    }
}
