package com.example.club_project.exception.custom;

public class ClubRuntimeException extends RuntimeException{
    public ClubRuntimeException(){}
    public ClubRuntimeException(String msg){
        super(msg);
    }
}
