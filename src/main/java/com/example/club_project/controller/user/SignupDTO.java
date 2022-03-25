package com.example.club_project.controller.user;

import com.example.club_project.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupDTO {

    @NotBlank(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    private String name;

    // 현재는 가정일뿐, 수정가능
    @NotBlank(message = "사용자 이름은 필수값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "사용자이름은 영어랑 숫자만 가능합니다.")
    private String nickname;

    private String university;

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

    @Builder
    private SignupDTO(String email, String password, String name,
                      String nickname, String university, String introduction){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.university = university;
        this.introduction = introduction;
    }

}
