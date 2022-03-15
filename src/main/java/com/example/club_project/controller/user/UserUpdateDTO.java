package com.example.club_project.controller.user;


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

    public static class Response{

        @NotEmpty
        private String email;

    }
}
