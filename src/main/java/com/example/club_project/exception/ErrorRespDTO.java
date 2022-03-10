package com.example.club_project.exception;

import lombok.*;

import java.util.Map;


// 수정 예정
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRespDTO {

    String exception;

    String message;

    Map<String, String> errors;
}

