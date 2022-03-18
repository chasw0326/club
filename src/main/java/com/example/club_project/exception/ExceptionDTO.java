package com.example.club_project.exception;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class ExceptionDTO {

    @NotEmpty
    String exception;

    @NotEmpty
    String message;


    public static ExceptionDTO toDto(Exception e){
        e.printStackTrace();
        return ExceptionDTO.builder()
                .exception(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
}
