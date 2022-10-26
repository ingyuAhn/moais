package com.assignment.api.member.mapper;

import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.core.domain.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberSignInResponseMapper {

    MemberSignInResponse memberSignInDto(String accessToken, Member member);
}
