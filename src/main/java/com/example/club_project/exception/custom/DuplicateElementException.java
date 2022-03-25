package com.example.club_project.exception.custom;

// 중복 체크용
public class DuplicateElementException extends ClubRuntimeException{
    public DuplicateElementException(){}
    public DuplicateElementException(String msg){
        super(msg);
    }
}
