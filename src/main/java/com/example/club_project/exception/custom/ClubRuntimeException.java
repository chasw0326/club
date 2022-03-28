package com.example.club_project.exception.custom;

//BaseException
public abstract class ClubRuntimeException extends RuntimeException{
    public ClubRuntimeException(){}
    public ClubRuntimeException(String msg){
        super(msg);
    }
}
