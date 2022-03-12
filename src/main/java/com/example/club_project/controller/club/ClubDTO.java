package com.example.club_project.controller.club;

import com.example.club_project.domain.Club;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class ClubDTO {

    /**
     * GET 복수 건 조회
     */
    @Getter
    public static class MultiSearchOption {
        List<String> categories;
        String university;
    }

    /**
     * GET 단 건 조회
     */
    @Getter
    public static class SingleSearchOption {
        private String name;
        private String university;
    }

    /**
     * POST
     */
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class RegisterRequest {
        private String name;
        private String address;
        private String university;
        private String description;
        private String categoryName;
        private String imageUrl;
    }

    /**
     * PUT
     */
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class UpdateRequest {
        private String name;
        private String address;
        private String university;
        private String description;
        private String categoryName;
        private String imageUrl;
    }

    /**
     * ResponseBody
     */
    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String address;
        private String university;
        private String description;
        private String imageUrl;
        private String category;

        public static ClubDTO.Response from(Club club) {
            return Response.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .address(club.getAddress())
                    .university(club.getUniversity())
                    .description(club.getDescription())
                    .imageUrl(StringUtils.isEmpty(club.getImageUrl()) ? club.getImageUrl() : "")
                    .category(club.getCategory().getName())
                    .build();
        }
    }
}
