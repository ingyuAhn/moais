package com.assignment.api.member.controller;

import com.assignment.api.config.message.ResponseMessage;
import com.assignment.api.member.service.MemberAwayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MemberAway", description = "회원탈퇴 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/member/away")
public class MemberAwayController {

    private final MemberAwayService memberAwayService;

    @Parameter(description = "로그인 회원의 accessToken", name = "ACCESS_TOKEN", in = ParameterIn.HEADER)
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 할 수 있다.")
    @PutMapping(value = "")
    public ResponseMessage memberAway() {
        Object memberId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberAwayService.memberAwayProcess(memberId);
        return new ResponseMessage(HttpStatus.OK, "success");
    }
}
