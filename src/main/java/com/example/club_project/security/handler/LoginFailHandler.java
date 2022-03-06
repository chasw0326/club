package com.example.club_project.security.handler;

import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class LoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                       HttpServletResponse response,
                                       AuthenticationException ex)
            throws IOException, ServletException{

        log.info("onAuthenticationFailure 시작");
        log.info("Login fail handler........................");
        log.info(ex.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = ex.getMessage();
        json.put("code", "401");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
