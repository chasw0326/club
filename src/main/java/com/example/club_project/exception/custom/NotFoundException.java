package com.example.club_project.exception.custom;

public class NotFoundException extends ClubRuntimeException{
    public NotFoundException(String msg){
        super(msg);
    }

}
