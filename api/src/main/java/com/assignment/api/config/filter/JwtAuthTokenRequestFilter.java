package com.assignment.api.config.filter;


import com.assignment.api.member.token.JwtTokenProvider;
import com.assignment.api.member.token.JwtTokenResolver;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Http Request 헤더에 ACCESS_TOKEN 이 담겨있다면 토큰의 유효성을 검증하고
 * principal 과 GrantedAuthority 리스트가 UsernamePasswordAuthenticationToken 에 담긴다.
 * UsernamePasswordAuthenticationToken 은 SecurityContextHolder 에 담긴다.
 */
@RequiredArgsConstructor
public class JwtAuthTokenRequestFilter extends OncePerRequestFilter {

    private final JwtTokenResolver jwtTokenResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        Optional<Claims> accessToken = jwtTokenResolver.resolveAccessToken(request);

        accessToken.ifPresent(token -> {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(token.get("memberId", Long.class),
                    "",
                    Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        });
        filterChain.doFilter(request, response);

    }
}
