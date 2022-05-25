package com.example.club_project.service.user;

import com.example.club_project.domain.User;
import com.example.club_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    private String password;

    @BeforeEach
    @Test
    void setUp() {
        password = "aaZZa44@";
        testUser = User.builder()
                .email("test@naver.com")
                .name("testName")
                .nickname("testNickname")
                .university("서울대학교")
                .introduction("안녕하세요")
                .password(passwordEncoder.encode(password))
                .build();

        testUser = userRepository.save(testUser);

    }



    @DisplayName("정상적인 회원가입")
    @Test
    void Should_ReturnUserId_WhenSignin_WithNormalValue() {
        User user = User.builder()
                .email("inha@naver.com")
                .name("inha")
                .nickname("inhahaha")
                .university("인하대학교")
                .introduction("안녕하세요")
                .password("abcABC123!@#")
                .build();

        Long userId = userService.signup(user);
        User result = userRepository.findById(userId).
                orElseThrow(() -> new EntityNotFoundException("테스트 실패"));

        assertEquals("inha@naver.com", result.getEmail());
        assertEquals("inha", result.getName());
        assertEquals("inhahaha", result.getNickname());
        assertEquals("인하대학교", result.getUniversity());
        assertEquals("안녕하세요", result.getIntroduction());
        assertTrue(passwordEncoder.
                matches("abcABC123!@#", result.getPassword()));
    }

    @DisplayName("이전 비밀번호랑 다른 비밀번호로 비밀번호 변경 요청")
    @Test
    void Should_ThrowException_WhenChangePassword_WithWrongPw() {
        Long principalId = testUser.getId();
        Throwable ex = assertThrows(IllegalArgumentException.class, () ->
                userService.updatePassword(principalId, password + "salt",
                "abcABC123!@#", "abcABC123!@#"));
        assertEquals("이전 비밀번호와 다릅니다.", ex.getMessage());
    }

    @DisplayName("변경할 비밀번호와 확인 비밀번호가 다를때")
    @Test
    void Should_ThrowException_WhenNewPwAndCheckPwIsDiff() {

        Long principalId = testUser.getId();

        Throwable ex = assertThrows(IllegalArgumentException.class, () ->
                userService.updatePassword(principalId, password,
                "abcABC123!@#", "ABCabc123!@#"));
        assertEquals("확인 비밀번호가 새 비밀번호와 다릅니다.", ex.getMessage());
    }

    @DisplayName("정상적인 비밀번호 변경")
    @Test
    void Should_ChangePw_WhenNormalRequest() {

        Long principalId = testUser.getId();

        userService.updatePassword(principalId, password,
                "abcABC123!@#", "abcABC123!@#");

        User result = userRepository.findById(principalId).
                orElseThrow(() -> new EntityNotFoundException("테스트 실패"));

        assertTrue(passwordEncoder.matches("abcABC123!@#", result.getPassword()));
    }

    @DisplayName("유저정보 수정 테스트")
    @Test
    void Should_UpdateUserInfo_WhenNormalRequest() {
        Long principalId = testUser.getId();
        String name = testUser.getName();
        String nickname = testUser.getNickname();
        String university = testUser.getUniversity();
        String intro = testUser.getIntroduction();

        userService.updateUser(principalId, "new" + name, "new" + nickname, "new" + university, "new" + intro);
        User result = userRepository.findById(principalId).
                orElseThrow(() -> new EntityNotFoundException("테스트 실패"));

        assertEquals("new" + name, result.getName());
        assertEquals("new" + nickname, result.getNickname());
        assertEquals("new" + university, result.getUniversity());
        assertEquals("new" + intro, result.getIntroduction());
    }
}
