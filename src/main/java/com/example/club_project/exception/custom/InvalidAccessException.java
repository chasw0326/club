package com.example.club_project.exception.custom;

// 가입하지도 않은 동아리 탈퇴시
public class InvalidAccessException extends ClubRuntimeException{
    public InvalidAccessException(){}
    public InvalidAccessException(String msg){super(msg);}
}
