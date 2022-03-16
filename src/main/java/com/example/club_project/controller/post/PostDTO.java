package com.example.club_project.controller.post;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


public class PostDTO {

    @Getter
    public static class Request {

        //TODO: 메시지 추가 예정
        @NotEmpty
        private String title;

        @NotEmpty
        private String content;
    }

    @Getter
    @Builder
    public static class Response {

        private String profileUrl;

        @NotEmpty
        private String nickname;

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        @NotEmpty
        private LocalDateTime createdAt;

        private Long commentCnt;

    }
}