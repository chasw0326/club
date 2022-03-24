package com.example.club_project.controller.user;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserUpdateDTO {

    @Getter
    public static class Request{

        @NotBlank
        private String name;

        @NotBlank
        private String nickname;

        @NotBlank
        private String introduction;

        @NotBlank
        private String university;
    }

    @Builder
    public static class Response{

        @NotBlank
        private String email;

    }
}