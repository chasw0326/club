package com.example.club_project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {

    private String name;

    private String gender;

    private String intro;

}
