package com.assignment.api.member.token;

import com.assignment.core.domain.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider extends AbstractJwtConfig{

    public String generateAccessToken(Member member) {
        Claims claims = createClaims(member);
        return generateAccessToken(claims);
    }

    private Claims createClaims(Member member) {
        Claims claims = Jwts.claims().setSubject(JwtConstant.SUB);
        claims.put(JwtConstant.MEMBER_ID, member.getMemberId());
        return claims;
    }

    private String generateAccessToken(Claims claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 30 * 60 * 1000L);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
