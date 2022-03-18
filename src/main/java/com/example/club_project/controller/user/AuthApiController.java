package com.example.club_project.controller.user;

import com.example.club_project.domain.User;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;

    /**
     * email
     * introduction
     * password (8 ~ 20글자, 대문자, 소문자, 숫자, 특수문자 모두 포함해야 한다.)
     * name
     * nickname
     * university
     * 을 보내야 한다.
     *
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupDTO signupDTO) {
        User user = signupDTO.toEntity(signupDTO);
        Long userId = userService.signup(user);
        return ResponseEntity.ok().body(userId);
    }

//     로그인은 auth/signin으로
//     email, password를 보내주세요 [POST]
//     응답으로는 jwt 토큰을 보내줍니다.
//     postman Authorization에서 Type Bearer Token에 담아서
//     다음 요청들을 보내주세요
}
