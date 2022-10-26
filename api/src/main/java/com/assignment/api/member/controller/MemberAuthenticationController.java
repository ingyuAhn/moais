package com.assignment.api.member.controller;


import com.assignment.api.config.message.ResponseDataMessage;
import com.assignment.api.config.message.ResponseMessage;
import com.assignment.api.member.dto.request.MemberSignInRequest;
import com.assignment.api.member.dto.request.MemberSignUpRequest;
import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.api.member.service.MemberAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Member authentication controller.
 */
@Tag(name = "Member", description = "회원가입 및 로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/member/authentication")
public class MemberAuthenticationController {

    private final MemberAuthenticationService memberAuthenticationService;

    /**
     * Member sign up response message.
     *
     * @param memberSignUpRequest the member sign up request
     * @return response message
     */
    @Operation(summary = "회원가입", description = "회원가입을 할 수 있다.")
    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage memberSignUp(@RequestBody @Valid MemberSignUpRequest memberSignUpRequest) {
        return memberAuthenticationService.memberSignUpProcess(memberSignUpRequest);

    }

    /**
     * Member sign in response data message.
     *
     * @param memberSignInRequest the member sign in request
     * @return ResponseDataMessage response data message
     */
    @Operation(summary = "로그인", description = "로그인을 할 수 있다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = MemberSignInResponse.class)))
    @PostMapping(value = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDataMessage memberSignIn(@RequestBody @Valid MemberSignInRequest memberSignInRequest) {
        MemberSignInResponse memberSignInResponse = memberAuthenticationService.memberSignInProcess(memberSignInRequest);
        return new ResponseDataMessage(HttpStatus.OK, "", memberSignInResponse);
    }
}
