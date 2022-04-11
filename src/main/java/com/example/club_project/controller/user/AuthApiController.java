package com.example.club_project.controller.user;

import com.example.club_project.domain.User;
import com.example.club_project.exception.custom.UnHandleException;
import com.example.club_project.util.upload.UploadUtil;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;
    private final UploadUtil uploadUtil;

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
    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void registerUser(@RequestPart @Valid SignupDTO signupDTO,
                             @RequestPart(required = false) MultipartFile profileImage) {

        User user = signupDTO.toEntity(signupDTO);
        Long userId = userService.signup(user);

        supplyAsync(() -> uploadUtil.upload(profileImage, "profile"))
            .thenAccept(profileImageUrl -> {
                log.info("supplyAsync::thenAccept part");
                userService.updateProfileImage(userId, profileImageUrl);
            })
            .exceptionally(throwable -> {
                if (throwable instanceof UnHandleException) {
                    log.warn("{}",throwable.getMessage(), throwable);
                } else if (throwable instanceof RuntimeException) {
                    log.warn("{}",throwable.getMessage(), throwable);
                }
                return null;
            });
    }


//     로그인은 auth/signin으로
//     email, password를 보내주세요 [POST]
//     응답으로는 jwt 토큰을 보내줍니다.
//     postman Authorization에서 Type Bearer Token에 담아서
//     다음 요청들을 보내주세요
}
