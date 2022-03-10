package com.example.club_project.controller.user;


import javax.validation.constraints.NotEmpty;

public class UserUpdateDTO {

    Long id;

    @NotEmpty
    String name;

    @NotEmpty
    String nickname;

    @NotEmpty
    String introduction;

    @NotEmpty
    String university;
}
