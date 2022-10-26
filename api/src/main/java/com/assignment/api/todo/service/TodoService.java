package com.assignment.api.todo.service;

import com.assignment.api.config.message.ResponseMessage;
import com.assignment.api.todo.dto.request.TodoWriteRequest;
import com.assignment.api.todo.dto.response.TodoListResponse;
import com.assignment.api.todo.mapper.TodoListResponseMapper;
import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.Todo;
import com.assignment.core.domain.entity.value.TodoStatus;
import com.assignment.core.repository.MemberRepository;
import com.assignment.core.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.List;

@Service
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final TodoListResponseMapper todoListResponseMapper;

    public TodoService(TodoRepository todoRepository,
                       MemberRepository memberRepository,
                       TodoListResponseMapper todoListResponseMapper) {
        this.todoRepository = todoRepository;
        this.memberRepository = memberRepository;
        this.todoListResponseMapper = todoListResponseMapper;
    }


    /**
     * Todo write.
     *
     * @param todoWriteRequest the todo write request
     * @param memberId         the member id
     */
    @Transactional
    public void todoWriteProcess(TodoWriteRequest todoWriteRequest, Object memberId) {
        Member member = memberValidationCheck(memberId);

        Todo todo = Todo.builder()
                .content(todoWriteRequest.getContent())
                .todoStatus(todoWriteRequest.getTodoStatus())
                .member(member)
                .build();

        todoRepository.save(todo);
    }

    /**
     * Todo status modify.
     *
     * @param todoStatus the todo status
     * @param memberId   the member id
     * @param todoId     the todo id
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage todoStatusModifyProcess(TodoStatus todoStatus, Object memberId, Long todoId) {
        Member member = memberValidationCheck(memberId);
        try {
            Todo todo = anotherMemberCheck(member.getMemberId(), todoId);
            todo.todoStatusModify(todoStatus);
        }catch (ValidationException e) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return new ResponseMessage(HttpStatus.OK, "success");
    }

    /**
     * Todo list process list.
     *
     * @param pageRequest the page request
     * @param memberId    the member id
     * @return the list
     */
    @Transactional(readOnly = true)
    public List<TodoListResponse> todoListProcess(PageRequest pageRequest, Object memberId) {
        Member member = memberValidationCheck(memberId);

        Page<Todo> todoPage = todoRepository.findByMember(member, pageRequest);

        return todoListResponseMapper.todoListDto(todoPage.getContent());

    }

    private Member memberValidationCheck(Object memberId) {
        return memberRepository.findById(Long.valueOf(memberId.toString()))
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 회원입니다."));
    }

    private Todo anotherMemberCheck(Long memberId, Long todoId) throws ValidationException {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않은 게시물입니다."));

        if (!todo.getMember().getMemberId().equals(memberId)) {
            throw new ValidationException("본인이 작성한 게시물만 수정이 가능합니다.");
        }
        return todo;


    }
}
