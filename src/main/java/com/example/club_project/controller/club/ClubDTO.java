package com.example.club_project.controller.club;

import com.example.club_project.domain.Club;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.*;

public class ClubDTO {

    /**
     * GET
     *
     * 조회 옵션
     */
    @Builder
    @Getter
    public static class SearchOption {
        List<Long> categories;
        String name;

        public static SearchOption of(String name, String category) {

            if (StringUtils.isEmpty(category)) {
                return SearchOption.builder()
                        .name(name)
                        .categories(Collections.emptyList())
                        .build();
            } else {
                List<Long> categoryIds = Arrays.stream(category.split(","))
                        .map(Long::valueOf).collect(toList());

                return SearchOption.builder()
                        .name(name)
                        .categories(categoryIds)
                        .build();
            }
        }
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
        private Long category;
        private String imageUrl;
    }

    /**
     * PUT
     */
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class UpdateRequest {
        @NotBlank(message = "name 필드는 반드시 있어야 합니다.")
        private String name;
        private String address;
        @NotBlank(message = "university 필드는 반드시 있어야 합니다.")
        private String university;
        private String description;
        private Long category;
        private String imageUrl;
    }

    /**
     * ResponseBody
     */
    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class Response {
        private Long id;
        private String name;
        private String address;
        private String university;
        private String description;
        private String imageUrl;
        private Long category;

        public static ClubDTO.Response from(Club club) {
            return Response.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .address(club.getAddress())
                    .university(club.getUniversity())
                    .description(club.getDescription())
                    .imageUrl(StringUtils.isEmpty(club.getImageUrl()) ? club.getImageUrl() : "")
                    .category(club.getCategory().getId())
                    .build();
        }
    }
}
