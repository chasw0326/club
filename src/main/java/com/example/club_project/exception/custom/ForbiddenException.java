package com.example.club_project.exception.custom;

public class ForbiddenException extends ClubRuntimeException {
    public ForbiddenException(String msg){
        super(msg);
    }

}
