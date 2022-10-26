package com.assignment.api.member.mapper;

import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.core.domain.entity.Member;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-26T23:21:57+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.jar, environment: Java 1.8.0_292 (AdoptOpenJDK)"
)
@Component
public class MemberSignInResponseMapperImpl implements MemberSignInResponseMapper {

    @Override
    public MemberSignInResponse memberSignInDto(String accessToken, Member member) {
        if ( accessToken == null && member == null ) {
            return null;
        }

        String accessToken1 = null;
        if ( accessToken != null ) {
            accessToken1 = accessToken;
        }
        String nickName = null;
        String email = null;
        if ( member != null ) {
            nickName = member.getNickName();
            email = member.getEmail();
        }

        MemberSignInResponse memberSignInResponse = new MemberSignInResponse( nickName, email, accessToken1 );

        return memberSignInResponse;
    }
}
