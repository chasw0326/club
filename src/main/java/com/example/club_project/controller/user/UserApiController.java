
package com.example.club_project.controller.user;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/update/info")
    public UserUpdateDTO.Response getUserIngo(@AuthenticationPrincipal AuthUserDTO authUserDTO){
        return userService.getUserUpdateRespDTO(authUserDTO.getId());
    }

    // AuthUser 사용법
    @PostMapping("/update/info")
    public void updateUserInfo(@AuthenticationPrincipal AuthUserDTO authUser,
                               @RequestBody @Valid UserUpdateDTO.Request userUpdateDTO) {
        userService.updateUserInfo(
                authUser.getId(),
                userUpdateDTO.getName(),
                userUpdateDTO.getNickname(),
                userUpdateDTO.getUniversity(),
                userUpdateDTO.getIntroduction()
        );
    }

    @GetMapping("/update/password")
    public PasswordDTO.Response getPasswordPageInfo(@AuthenticationPrincipal AuthUserDTO authUserDTO){
        return userService.getPasswordRespDTO(authUserDTO.getId());
    }

    @PostMapping("/update/password")
    public void updatePassword(@AuthenticationPrincipal AuthUserDTO authUserDTO,
                               @RequestBody @Valid PasswordDTO.Request passwordDTO) {

        userService.updatePassword(
                authUserDTO.getId(),
                passwordDTO.getOldPw(),
                passwordDTO.getNewPw(),
                passwordDTO.getCheckPw()
        );
    }
}