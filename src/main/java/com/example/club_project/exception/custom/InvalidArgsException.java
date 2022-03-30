package com.example.club_project.exception.custom;

public class InvalidArgsException extends ClubRuntimeException {
    public InvalidArgsException(String msg) {
        super(msg);
    }
}
