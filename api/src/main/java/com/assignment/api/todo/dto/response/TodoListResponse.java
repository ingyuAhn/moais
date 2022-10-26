package com.assignment.api.todo.dto.response;

import com.assignment.core.domain.entity.value.MemberStatus;
import com.assignment.core.domain.entity.value.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoListResponse {

    private String content;

    private TodoStatus todoStatus;

}
