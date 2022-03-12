package com.example.club_project.controller;

import com.example.club_project.controller.user.SignupDTO;
import com.example.club_project.security.util.JWTUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTUtil jwtUtil;

    private String token;

    @DisplayName("회원가입 테스트")
    @Test
    @Order(value = 1)
    void Should_Signup(){
        SignupDTO signupDTO = SignupDTO.builder()
                .email("samsung@naver.com")
                .introduction("hi! I'm samsung")
                .password("abcABC123!@#")
                .name("samsung")
                .nickname("apple")
                .university("삼성대학교")
                .build();

        webTestClient.post().uri("/auth/signup")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(signupDTO)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("로그인 테스트")
    @Test
    @Order(value = 2)
    void Should_ExtractEmail_WhenValidateJWT() throws Exception {
        String postRequestBody = new JSONObject()
                .put("email", "samsung@naver.com")
                .put("password", "abcABC123!@#")
                .toString();


        token = WebClient.create()
                .post()
                .uri("localhost:" + port + "/auth/signin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(postRequestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertEquals("samsung@naver.com", jwtUtil.validateAndExtract(token));
    }

}
