package com.example.club_project.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardDTO {


    @NotEmpty(message = "제목을 입력하세요.")
    private String title;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    private String type;
}
