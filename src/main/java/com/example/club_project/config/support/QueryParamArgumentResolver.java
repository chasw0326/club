package com.example.club_project.config.support;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class QueryParamArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${club.query-param.offset-name:page}")
    private String offsetParam;

    @Value("${club.query-param.limit-name:size}")
    private String limitParam;

    @Value("${club.query-param.offset-size:0}")
    private int defaultOffset;

    @Value("${club.query-param.limit-size:20}")
    private int defaultLimit;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String offsetString = webRequest.getParameter(offsetParam);
        String limitString = webRequest.getParameter(limitParam);

        int offset = NumberUtils.toInt(offsetString, defaultOffset);
        int limit = NumberUtils.toInt(limitString, defaultLimit);

        offset = offset < 0 ? defaultOffset : offset;
        limit = limit < 0 ? defaultLimit : limit;

        return PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "id"));
    }
}
