package com.example.club_project.exception;

import com.example.club_project.exception.custom.ClubRuntimeException;
import com.example.club_project.exception.custom.DuplicateElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid exception catch
    @ExceptionHandler({MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentConversionNotSupportedException.class})
    public ResponseEntity<ValidExceptionDTO> validExceptionHandler(BindException ex) {
        log.warn("valid Error: {}", ex.getMessage());
        ex.printStackTrace();
        ValidExceptionDTO dto = ValidExceptionDTO.toDto(ex);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    // return CONFLICT
    @ExceptionHandler({DuplicateElementException.class})
    public ResponseEntity<String> duplicateElementHandler(DuplicateElementException ex){
        String message = ex.getMessage();
        ex.printStackTrace();
        log.warn("DuplicateElementException: {}", message);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ClubRuntimeException.class})
    public ResponseEntity<String> clubRuntimeHandler(ClubRuntimeException ex){
        String message = ex.getMessage();
        ex.printStackTrace();
        log.warn("ClubRuntimeException: {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> runtimeHandler(RuntimeException ex) {
        String message = ex.getMessage();
        ex.printStackTrace();
        log.error("RuntimeException: {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
