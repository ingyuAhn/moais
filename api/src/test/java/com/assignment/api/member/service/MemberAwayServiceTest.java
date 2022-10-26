package com.assignment.api.member.service;

import com.assignment.api.member.dto.request.MemberSignInRequest;
import com.assignment.api.member.dto.request.MemberSignUpRequest;
import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.api.member.token.JwtTokenResolver;
import com.assignment.core.domain.entity.value.MemberStatus;
import com.assignment.core.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberAwayServiceTest {

    @Autowired
    private MemberAwayService memberAwayService;

    @Autowired
    private MemberAuthenticationService memberAuthenticationService;

    @Autowired
    private JwtTokenResolver jwtTokenResolver;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원탈퇴() {

        MemberSignUpRequest memberSignUpRequest =
                new MemberSignUpRequest("email@naver.com", "password", "nickName");

        memberAuthenticationService.memberSignUpProcess(memberSignUpRequest);

        MemberSignInRequest signInRequest =
                new MemberSignInRequest("email@naver.com", "password");

        MemberSignInResponse memberSignInResponse =  memberAuthenticationService.memberSignInProcess(signInRequest);

        String accessToken = memberSignInResponse.getAccessToken();

        Long memberId = jwtTokenResolver.parseClaims(accessToken).get("memberId", Long.class);

        memberAwayService.memberAwayProcess(memberId);

        memberRepository.findById(memberId).ifPresent(member -> {
            Assert.assertEquals(member.getMemberStatus(), MemberStatus.REMOVED_STANDARD);
        });
    }
}