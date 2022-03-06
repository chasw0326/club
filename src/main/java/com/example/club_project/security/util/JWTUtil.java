package com.example.club_project.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {

    private final String SECRET_KEY = "q1!w2@e3#r4$t5%";

    private final long EXPIRE = 60 * 24 * 30;

    public String generateToken(String content) throws Exception {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(EXPIRE).toInstant()))
                .claim("sub", content)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes("UTF-8"))
                .compact();
    }

    public String validateAndExtract(String tokenStr) throws Exception {

        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes("UTF-8")).parseClaimsJws(tokenStr);

            log.info(defaultJws);
            log.info(defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            log.info("--------------------------------");

            contentValue = claims.getSubject();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }
}

