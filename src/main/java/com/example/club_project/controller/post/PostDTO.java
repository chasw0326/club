package com.example.club_project.controller.post;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class PostDTO {

    @Getter
    public static class Request {

        //TODO: 메시지 추가 예정
        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }


    @Getter
    @Builder
    public static class Response {

        private String profileUrl;

        @NotBlank
        private String nickname;

        @NotBlank
        private String title;

        // blank는 허용
        @NotEmpty
        private String content;

        @NotBlank
        private LocalDateTime createdAt;

        private Long commentCnt;

    }
}