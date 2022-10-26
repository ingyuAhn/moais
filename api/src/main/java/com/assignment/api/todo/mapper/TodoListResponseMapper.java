package com.assignment.api.todo.mapper;

import com.assignment.api.todo.dto.response.TodoListResponse;
import com.assignment.core.domain.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoListResponseMapper {

    List<TodoListResponse> todoListDto(List<Todo> todoListø);

}
