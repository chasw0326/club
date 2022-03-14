package com.example.club_project.controller.user;


import javax.validation.constraints.NotEmpty;

public class UserUpdateDTO {

    public static class Request{

        @NotEmpty
        String name;

        @NotEmpty
        String nickname;

        @NotEmpty
        String introduction;

        @NotEmpty
        String university;
    }

    public static class Response{

        @NotEmpty
        String email;

    }
}
