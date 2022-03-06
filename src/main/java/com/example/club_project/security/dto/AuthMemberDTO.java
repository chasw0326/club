package com.example.club_project.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;


@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;

    private String password;

    private String name;

    private Long id;

    private Map<String, Object> attr;

    //Social Login User
    public AuthMemberDTO(
            String username,
            String password,
            String name,
            Collection<? extends GrantedAuthority> authorities,
            Long id,
            Map<String, Object> attr) {

        this(username, password, name, authorities, id);
        this.attr = attr;
    }

    public AuthMemberDTO(
            String username,
            String password,
            String name,
            Collection<? extends GrantedAuthority> authorities,
            Long id) {

        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}

