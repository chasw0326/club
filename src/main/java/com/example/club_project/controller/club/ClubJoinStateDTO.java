package com.example.club_project.controller.club;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;


public class ClubJoinStateDTO {

    /**
     * POST
     */
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RegisterRequest {
        private String name;
        private String address;
        private String description;
        private Long category;
        private String imageUrl;
    }



    /**
     * ResponseBody
     * 클럽 가입과 관련한 사용자 정보를 나타낸다.
     */
    @Builder
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        private Long userId;
        private String email;
        private String name;
        private String nickname;
        private String profileUrl;
        private Long clubId;
        private Integer joinStateCode;
    }
}
