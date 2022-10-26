package com.assignment.api.member.service;

import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.value.MemberStatus;
import com.assignment.core.repository.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

/**
 * The type Member away service.
 */
@Service
public class MemberAwayService {

    private final MemberRepository memberRepository;

    public MemberAwayService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * Member away process.
     *
     * @param memberId the member id
     */
    @Transactional(rollbackFor = Exception.class)
    public void memberAwayProcess(Object memberId) {
        Member member = memberRepository.findById(Long.valueOf(memberId.toString()))
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 회원입니다."));

        member.modifyMemberStatus(MemberStatus.REMOVED_STANDARD);

    }
}
