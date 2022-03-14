package com.example.club_project.repository;

import com.example.club_project.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void setUp() {
        String password = "qwe123!@#";
        User testUser = User.builder()
                .email("test@naver.com")
                .name("testName")
                .nickname("testNickname")
                .university("서울대학교")
                .introduction("안녕하세요")
                .password(password)
                .build();

        userRepository.save(testUser);

        User user = userRepository.findById(1L).orElse(null);
        System.out.println(user);
    }



}
