package com.example.club_project.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
// 도메인의 User가 아닌 UserDetails의 User
public class AuthUserDTO extends User {

    private String email;

    private String password;

    private Long id;

    public AuthUserDTO(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Long id) {

        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.id = id;
    }
}
