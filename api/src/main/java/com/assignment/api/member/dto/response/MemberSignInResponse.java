package com.assignment.api.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignInResponse {

    private String nickName;

    private String email;

    private String accessToken;
}
