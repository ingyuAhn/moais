package com.assignment.api.member.token;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenResolver extends AbstractJwtConfig{

    public Optional<Claims> resolveAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("ACCESS_TOKEN");

        if (Objects.nonNull(accessToken)) {
            return Optional.of(parseClaims(accessToken));
        }
        return Optional.empty();
    }

    public Claims parseClaims(String accessToken) {
        Jws<Claims> jwts = null;
        try {
            jwts = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(accessToken);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT");
        }
        return jwts.getBody();
    }


}
