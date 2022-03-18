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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupDTO signupDTO) {
        User user = signupDTO.toEntity(signupDTO);
        Long userId = userService.signup(user);
        return ResponseEntity.ok().body(userId);
    }
}
