package com.example.club_project.controller.category;

import com.example.club_project.domain.Category;
import lombok.Builder;
import lombok.Getter;

public class CategoryDTO {

    /**
     * POST
     */
    @Getter
    public static class RegisterRequest {
        private String name;
        private String description;
    }

    /**
     * PUT
     */
    @Getter
    public static class UpdateRequest {
        private String name;
        private String description;
    }

    @Builder
    @Getter
    public static class Response {
        private Long id;
        private String name;
        private String description;

        public static CategoryDTO.Response from(Category category) {
            return CategoryDTO.Response.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
        }
    }
}
