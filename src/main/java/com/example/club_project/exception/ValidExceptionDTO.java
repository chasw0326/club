package com.example.club_project.exception;

import lombok.*;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;


@Getter
@Builder
public class ValidExceptionDTO {

    @NotEmpty
    String exception;

    Map<String, String> errors;

    public static ValidExceptionDTO toDto(BindException e){
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ValidExceptionDTO.builder()
                .exception(e.getClass().getSimpleName())
                .errors(errors)
                .build();
    }
}

