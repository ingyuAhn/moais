package com.assignment.api.member.service;

import com.assignment.api.config.message.ResponseMessage;
import com.assignment.api.member.dto.request.MemberSignInRequest;
import com.assignment.api.member.dto.request.MemberSignUpRequest;
import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.api.member.mapper.MemberSignInResponseMapper;
import com.assignment.api.member.token.JwtTokenProvider;
import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.value.MemberStatus;
import com.assignment.core.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
public class MemberAuthenticationService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberSignInResponseMapper memberSignInResponseMapper;


    public MemberAuthenticationService(MemberRepository memberRepository,
                                       AuthenticationManager authenticationManager,
                                       JwtTokenProvider jwtTokenProvider,
                                       MemberSignInResponseMapper memberSignInResponseMapper) {
        this.memberRepository = memberRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberSignInResponseMapper = memberSignInResponseMapper;
    }

    /**
     * Member sign up process.
     *
     * @param memberSignUpRequest
     */
    @Transactional
    public ResponseMessage memberSignUpProcess(MemberSignUpRequest memberSignUpRequest){

        try{
            alreadyExistsEmail(memberSignUpRequest.getEmail());
            alreadyExistsNickName(memberSignUpRequest.getNickName());
        }catch (Exception e) {

            return new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        String password = BCrypt.hashpw(memberSignUpRequest.getPassword(), BCrypt.gensalt());

        Member member = Member.builder()
                .email(memberSignUpRequest.getEmail())
                .password(password)
                .nickName(memberSignUpRequest.getNickName())
                .build();

        memberRepository.save(member);
        return new ResponseMessage(HttpStatus.OK, "success");
    }

    /**
     * Member sign in process member sign in response.
     *
     * @param memberSignInRequest the member sign in request
     * @return 회원정보와 AccessToken Return
     */
    public MemberSignInResponse memberSignInProcess(MemberSignInRequest memberSignInRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        memberSignInRequest.getEmail(),
                        memberSignInRequest.getPassword()
                )
        );

        MemberSignInResponse memberSignInResponse = commonAuthentication(authenticate);
        return memberSignInResponse;
    }


    /**
     * Common authentication member sign in response.
     *
     * @param authenticate the authenticate
     * @return the member sign in response
     */
    public MemberSignInResponse commonAuthentication(Authentication authenticate) {

        Long memberId = Long.valueOf(authenticate.getPrincipal().toString());
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        String accessToken = jwtTokenProvider.generateAccessToken(member);

        MemberSignInResponse memberSignInResponse = memberSignInResponseMapper.memberSignInDto(accessToken, member);
        return memberSignInResponse;
    }


    private void alreadyExistsEmail(String email){
        memberRepository.findByEmailAndMemberStatus(email, MemberStatus.ACTIVE)
                .ifPresent(member -> {
                    throw new EntityExistsException("존재하는 회원입니다. email :" + member.getEmail());
                });
    }

    private void alreadyExistsNickName(String nickName) {
        memberRepository.findByNickName(nickName)
                .ifPresent(member -> {
                    throw new EntityExistsException("존재하는 닉네임입니다. nickName :" + member.getNickName());
                });
    }

}
