package com.example.club_project.exception.custom;

public class DuplicateElementException extends ClubRuntimeException{
    public DuplicateElementException(){}
    public DuplicateElementException(String msg){
        super(msg);
    }
}
