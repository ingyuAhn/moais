package com.assignment.api.member.token;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.Base64;

public abstract class AbstractJwtConfig {

    @Value("${jwt.secret}")
    protected String jwtSecret;

    @Value("${jwt.access-token-expiration}")
    protected Long jwtAccessTokenExpiration;

    @PostConstruct
    protected void encodingSecretKey() {
        this.jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    protected static class JwtConstant {
        public final static String SUB = "member";
        public final static String MEMBER_ID = "memberId";
    }
}
