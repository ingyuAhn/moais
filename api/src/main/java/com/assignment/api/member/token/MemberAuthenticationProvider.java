package com.assignment.api.member.token;


import com.assignment.core.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<UserDetails> userDetails = Optional.ofNullable(
                memberDetailsService
                        .loadUserByUsername(authentication.getPrincipal().toString())
        );
        Member member = userDetails
                .map(user -> (Member) user)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        if (!matchPassword(member.getPassword(), authentication.getCredentials().toString())) {
            throw new ValidationException("비밀번호가 일치하지 않습니다.");
        }

        Authentication authorizedToken = createAuthenticationToken(member);
        SecurityContextHolder.getContext().setAuthentication(authorizedToken);
        return authorizedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean matchPassword(String userPassword, String requestPassword) {
        return BCrypt.checkpw(requestPassword, userPassword);
    }

    private Authentication createAuthenticationToken(Member member) {
        return new UsernamePasswordAuthenticationToken(member.getMemberId(), "", Collections.emptyList());
    }
}
