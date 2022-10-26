package com.assignment.api.member.token;

import com.assignment.core.domain.entity.Member;
import com.assignment.core.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void tokenCreate() {

        Member member = Member.builder()
                .password("password")
                .nickName("nickName")
                .build();
        memberRepository.save(member);

        memberRepository.findById(Long.valueOf(1)).ifPresent(member1 -> {
            String accessToken = jwtTokenProvider.generateAccessToken(member1);
            Assert.assertNotNull(accessToken);

        });


    }
}