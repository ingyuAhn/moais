package com.assignment.api.member.service;

import com.assignment.api.member.dto.request.MemberSignInRequest;
import com.assignment.api.member.dto.request.MemberSignUpRequest;
import com.assignment.api.member.token.JwtTokenResolver;
import com.assignment.core.domain.entity.value.MemberStatus;
import com.assignment.core.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberAuthenticationServiceTest {


    @Autowired
    private MemberAuthenticationService memberAuthenticationService;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void 회원가입테스트() {
        MemberSignUpRequest memberSignUpRequest =
                new MemberSignUpRequest("email@naver.com", "password", "nickName");

        memberAuthenticationService.memberSignUpProcess(memberSignUpRequest);

        memberRepository.findByEmailAndMemberStatus("email@naver.com", MemberStatus.ACTIVE).ifPresent(
                member -> {
                    Assert.assertEquals(member.getMemberId(), Long.valueOf(1));
                }
        );
    }

    @Test
    void 로그인테스트() {
        MemberSignUpRequest memberSignUpRequest =
                new MemberSignUpRequest("email@naver.com", "password", "nickName");

        memberAuthenticationService.memberSignUpProcess(memberSignUpRequest);

        MemberSignInRequest signInRequest =
                new MemberSignInRequest("email@naver.com", "password");

        memberAuthenticationService.memberSignInProcess(signInRequest);
    }
}