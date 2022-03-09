package com.example.club_project.controller.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordDTO {

    static class Response{
        String username;
        String profile_url;
    }

    static class Request{
        @NotEmpty(message = "이전 비밀번호 값이 없습니다.")
        String oldPw;
        @NotNull(message = "새 비밀번호 값이 없습니다.")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        String newPw;
        @NotEmpty(message = "새 비밀번호 확인값이 없습니다.")
        String checkPw;
    }
}