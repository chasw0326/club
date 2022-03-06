package com.example.club_project.security.filter;

import com.example.club_project.security.dto.AuthMemberDTO;
import com.example.club_project.security.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessUrl, JWTUtil jwtUtil){
        super(defaultFilterProcessUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException{
        log.info("attemptAuthentication 시작");
        log.info("---------------ApiLoginFilter---------------");

        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.readValue(request.getInputStream(), ObjectNode.class);

        if(objectNode.get("email") == null || objectNode.get("password") == null){
            throw new BadCredentialsException("이메일이나 비밀번호를 확인하세요.");
        }

        String email = objectNode.get("email").asText();
        String password = objectNode.get("password").asText();

        log.info("email: " + email);
//        log.info("password: " + password);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, password);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication)
        throws IOException, ServletException{

        log.info("successfulAuthentication 시작");
        log.info("------------ApiLoginFilter------------");
        log.info("SuccessfulAuthentication: " + authentication);
        log.info(authentication.getPrincipal());


        String email = ((AuthMemberDTO)authentication.getPrincipal()).getUsername();
        String token = null;
        try {
            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());

            log.info(token);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
