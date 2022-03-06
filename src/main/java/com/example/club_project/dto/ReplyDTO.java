package com.example.club_project.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDTO {

    @NotEmpty(message = "댓글 내용을 입력하세요.")
    private String text;

    private Long bno;

    private Long rno;
}
