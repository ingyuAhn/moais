package com.assignment.api.todo.mapper;

import com.assignment.api.todo.dto.response.TodoListResponse;
import com.assignment.core.domain.entity.Todo;
import com.assignment.core.domain.entity.value.TodoStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-26T23:21:57+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.jar, environment: Java 1.8.0_292 (AdoptOpenJDK)"
)
@Component
public class TodoListResponseMapperImpl implements TodoListResponseMapper {

    @Override
    public List<TodoListResponse> todoListDto(List<Todo> todoListø) {
        if ( todoListø == null ) {
            return null;
        }

        List<TodoListResponse> list = new ArrayList<TodoListResponse>( todoListø.size() );
        for ( Todo todo : todoListø ) {
            list.add( todoToTodoListResponse( todo ) );
        }

        return list;
    }

    protected TodoListResponse todoToTodoListResponse(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        String content = null;
        TodoStatus todoStatus = null;

        content = todo.getContent();
        todoStatus = todo.getTodoStatus();

        TodoListResponse todoListResponse = new TodoListResponse( content, todoStatus );

        return todoListResponse;
    }
}
