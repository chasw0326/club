package com.example.club_project.security.handler;


import com.example.club_project.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException{

        log.info("----------------------------------------------");
        log.info("onAuthenticationSuccess 시작");


        AuthMemberDTO authMember = (AuthMemberDTO) authentication.getPrincipal();


//        boolean fromSocial = authMember.isFromSocial();

//        log.info("Need Modify Member?" + fromSocial);
//
//        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
//
//        if(fromSocial && passwordResult){
//            redirectStrategy.sendRedirect(request, response, "소셜로그인 패스워드 이름 변경 주소");
//        }
    }
}
