package com.example.club_project.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {

    @NotEmpty(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "사용자 이름은 필수값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "사용자이름은 영어랑 숫자만 가능합니다.")
    private String username;

    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotEmpty(message = "이름은 필수값 입니다.")
    private String name;

    private Long id;
}