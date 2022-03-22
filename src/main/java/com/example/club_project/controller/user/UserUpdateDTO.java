package com.example.club_project.controller.user;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

public class UserUpdateDTO {

    @Getter
    public static class Request{

        @NotEmpty
        private String name;

        @NotEmpty
        private String nickname;

        @NotEmpty
        private String introduction;

        @NotEmpty
        private String university;
    }

    @Builder
    public static class Response{

        @NotEmpty
        private String email;

    }
}