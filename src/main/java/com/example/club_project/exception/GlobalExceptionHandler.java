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
        log.error("valid Error: {}", ex.getMessage());
        ValidExceptionDTO dto = ValidExceptionDTO.toDto(ex);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    // return CONFLICT
    @ExceptionHandler({DuplicateElementException.class})
    public ResponseEntity<ExceptionDTO> duplicateElementHandler(DuplicateElementException ex){
        log.error("DuplicateElementException: {}", ex.getMessage());
        ExceptionDTO dto = ExceptionDTO.toDto(ex);
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ClubRuntimeException.class})
    public ResponseEntity<ExceptionDTO> clubRuntimeHandler(ClubRuntimeException ex){
        log.error("ClubRuntimeException: {}", ex.getMessage());
        ExceptionDTO dto = ExceptionDTO.toDto(ex);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ExceptionDTO> runtimeHandler(RuntimeException ex) {
        log.error("RuntimeException: {}", ex.getMessage());
        ExceptionDTO dto = ExceptionDTO.toDto(ex);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
