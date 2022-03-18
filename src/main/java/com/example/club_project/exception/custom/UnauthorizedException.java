package com.example.club_project.exception.custom;

public class UnauthorizedException extends ClubRuntimeException {
    public UnauthorizedException(){}
    public UnauthorizedException(String msg){
        super(msg);
    }

}
