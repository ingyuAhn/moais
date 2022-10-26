package com.assignment.api.todo.service;

import com.assignment.api.member.dto.request.MemberSignInRequest;
import com.assignment.api.member.dto.request.MemberSignUpRequest;
import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.api.member.service.MemberAuthenticationService;
import com.assignment.api.member.token.JwtTokenResolver;
import com.assignment.api.todo.dto.request.TodoWriteRequest;
import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.value.TodoStatus;
import com.assignment.core.repository.MemberRepository;
import com.assignment.core.repository.TodoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    void memberCreate() {

        List<Member> memberList = new ArrayList<>();

        Member member1 = new Member("email1@naver.com", "password", "nickName");
        Member member2 = new Member("email2@naver.com", "password", "nickName");
        Member member3 = new Member("email3@naver.com", "password", "nickName");

        memberList.add(member1);
        memberList.add(member2);
        memberList.add(member3);

        memberRepository.saveAll(memberList);

        TodoWriteRequest todoWriteRequest1 = new TodoWriteRequest("chest", TodoStatus.COMPLETE);
        TodoWriteRequest todoWriteRequest2 = new TodoWriteRequest("back", TodoStatus.COMPLETE);
        TodoWriteRequest todoWriteRequest3 = new TodoWriteRequest("leg", TodoStatus.COMPLETE);

        memberRepository.findById(Long.valueOf(1)).ifPresent(member -> {
            todoService.todoWriteProcess(todoWriteRequest1, member.getMemberId());
        });

        memberRepository.findById(Long.valueOf(2)).ifPresent(member -> {
            todoService.todoWriteProcess(todoWriteRequest2, member.getMemberId());
        });

        memberRepository.findById(Long.valueOf(3)).ifPresent(member -> {
            todoService.todoWriteProcess(todoWriteRequest3, member.getMemberId());
        });


    }

    @Test
    void todo수정테스트(){
        todoRepository.findById(Long.valueOf(1)).ifPresent(todo -> {
            System.out.println("#####todoMember" + todo.getMember().getMemberId());
            todoService.todoStatusModifyProcess(TodoStatus.PROGRESS, Long.valueOf(1), Long.valueOf(1));
        });

        todoRepository.findById(Long.valueOf(2)).ifPresent(todo -> {
            System.out.println("#####todoMember" + todo.getMember().getMemberId());
            todoService.todoStatusModifyProcess(TodoStatus.PROGRESS, Long.valueOf(2), Long.valueOf(2));
        });

        todoRepository.findById(Long.valueOf(3)).ifPresent(todo -> {
            System.out.println("#####todoMember" + todo.getMember().getMemberId());

            todoService.todoStatusModifyProcess(TodoStatus.PROGRESS, Long.valueOf(3), Long.valueOf(3));
        });

    }



}