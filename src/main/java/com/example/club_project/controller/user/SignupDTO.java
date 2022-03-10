package com.example.club_project.controller.user;

import com.example.club_project.domain.User;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class SignupDTO {

    @NotEmpty(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    private String name;

    @NotEmpty(message = "사용자 이름은 필수값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "사용자이름은 영어랑 숫자만 가능합니다.")
    private String nickname;

    private String university;

    // 소개는 유저정보 수정에서 수정하려함
    @Nullable
    private String introduction;

    public User toEntity(SignupDTO signupDTO) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .university(university)
                .introduction(introduction)
                .build();
    }

}
