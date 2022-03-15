package com.example.club_project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


@Getter
@AllArgsConstructor
public enum JoinState {
    MASTER(1,"관리자"),
    MANAGER(2,"운영진"),
    MEMBER(3,"일반 회원"),
    NOT_JOINED(4,"미가입");

    private static final Map<Integer, JoinState> joinStateMap =
            Stream.of(values()).collect(toMap(JoinState::getCode, value -> value));

    private Integer code;
    private String state;

    public static JoinState from(int JoinStateCode) {
        JoinState joinState = joinStateMap.get(JoinStateCode);
        if (ObjectUtils.isEmpty(joinState)) {
            throw new IllegalArgumentException("잘못된 JoinStateCode 타입입니다.");
        }

        return joinState;
    }
}
