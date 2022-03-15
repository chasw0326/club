
package com.example.club_project.controller.user;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // AuthUser 사용법
    @PostMapping("/update")
    public ResponseEntity<?> updateUserInfo(@AuthenticationPrincipal AuthUserDTO authUser,
                                            @RequestBody @Valid UserUpdateDTO.Request userUpdateDTO) {
        userService.updateUserInfo(
                authUser.getId(),
                userUpdateDTO.getName(),
                userUpdateDTO.getNickname(),
                userUpdateDTO.getUniversity(),
                userUpdateDTO.getIntroduction()
        );

        return ResponseEntity.ok().body("update");
    }
}