package com.assignment.api.member.token;


import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.value.MemberStatus;
import com.assignment.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        return memberRepository.findByEmailAndMemberStatus(name, MemberStatus.ACTIVE).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

    }
}
