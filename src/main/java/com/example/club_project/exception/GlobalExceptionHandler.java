package com.example.club_project.exception;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid exception catch
    @ExceptionHandler({MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentConversionNotSupportedException.class})
    public ResponseEntity<?> validExceptionHandler(BindException e) {
        log.error("valid Error: {}", e.getMessage());
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        ErrorRespDTO dto = ErrorRespDTO.builder()
                .exception(e.getClass().getSimpleName())
                .errors(errors)
                .build();
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> illegalArgHandler(IllegalArgumentException e) {
        log.error("accessDeniedHandler: {}", e.getMessage());
        e.printStackTrace();
        ErrorRespDTO dto = errorToDTO(e);
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> runtimeHandler(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage());
        e.printStackTrace();
        ErrorRespDTO dto = errorToDTO(e);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    private ErrorRespDTO errorToDTO(Exception e) {
        return ErrorRespDTO.builder()
                .exception(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
}
