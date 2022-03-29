package com.example.club_project.exception.custom;

// 동아리를 이미 가입했거나 중복값 예외
public class AlreadyExistsException extends ClubRuntimeException{
    public AlreadyExistsException(){}
    public AlreadyExistsException(String msg){super(msg);}
}
