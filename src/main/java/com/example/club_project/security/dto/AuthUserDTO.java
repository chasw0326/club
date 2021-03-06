package com.example.club_project.security.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Slf4j
@Getter
// 도메인의 User가 아닌 UserDetails의 User
public class AuthUserDTO extends User {

    private String email;

    private String password;

    private Long id;

    private String university;

    public AuthUserDTO(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Long id,
            String university) {

        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.id = id;
        this.university = university;
    }
}