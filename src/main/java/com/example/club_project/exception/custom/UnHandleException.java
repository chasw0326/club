package com.example.club_project.exception.custom;

/**
 * RuntimeException 과 같은 의미이지만 별도의 로깅처리를 위한 Exception
 */
public class UnHandleException extends ClubRuntimeException {
    public UnHandleException(String msg) {
        super(msg);
    }
}
