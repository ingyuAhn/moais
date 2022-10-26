package com.assignment.core.repository;

import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.Todo;
import com.assignment.core.domain.entity.value.TodoStatus;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void memberCreate() {
        Member member = Member.builder()
                .email("ehowl9509@naver.com")
                .password("password")
                .nickName("nickName")
                .build();
        memberRepository.save(member);

        Member member2 = Member.builder()
                .email("ehowl95092@naver.com")
                .password("password")
                .nickName("nickName")
                .build();
        memberRepository.save(member2);

        for(int i=0; i < 10; i++) {
            Todo todo = Todo.builder()
                    .content("오늘 운동은 하체")
                    .member(member)
                    .todoStatus(TodoStatus.COMPLETE)
                    .build();
            todoRepository.save(todo);

            Todo todo2 = Todo.builder()
                    .content("오늘 운동은 하체")
                    .member(member2)
                    .todoStatus(TodoStatus.COMPLETE)
                    .build();
            todoRepository.save(todo2);
        }
    }

    @Test
    void todoList() {

        Member member = memberRepository.findById(Long.valueOf(1)).get();

        Pageable pageable = PageRequest.of(0,20, Sort.Direction.DESC,"createDate");

        Page<Todo> todoPage = todoRepository.findByMember(member, pageable);

        todoPage.getContent().forEach(todo -> {
            Assert.assertEquals(todo.getMember().getMemberId(), Long.valueOf(1));
        });

    }

}