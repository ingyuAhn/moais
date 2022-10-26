package com.assignment.core.repository;

import com.assignment.core.domain.entity.Member;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void jpatest() {

        Member member = Member.builder()
                .password("password")
                .email("ehowl9509@naver.com")
                .nickName("nickName")
                .build();
        memberRepository.save(member);

        memberRepository.findById(Long.valueOf(1)).ifPresent(member1 -> {
            Assert.assertEquals(member1.getNickName(), "nickName");

        });
    }

}