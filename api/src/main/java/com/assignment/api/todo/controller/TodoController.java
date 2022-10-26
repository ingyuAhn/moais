package com.assignment.api.todo.controller;


import com.assignment.api.config.message.PagingProperties;
import com.assignment.api.config.message.ResponseDataMessage;
import com.assignment.api.config.message.ResponseMessage;
import com.assignment.api.member.dto.response.MemberSignInResponse;
import com.assignment.api.todo.dto.request.TodoWriteRequest;
import com.assignment.api.todo.dto.response.TodoListResponse;
import com.assignment.api.todo.service.TodoService;
import com.assignment.core.domain.entity.value.TodoStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Todo", description = "Todo 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/todo/manage")
public class TodoController {

    private final TodoService todoService;

    @Parameter(description = "로그인 회원의 accessToken", name = "ACCESS_TOKEN", in = ParameterIn.HEADER)
    @Operation(summary = "TODO 작성", description = "TODO를 작성 할 수 있다.")
    @PostMapping(value = "/write")
    public ResponseMessage todoWrite(@Valid @RequestBody TodoWriteRequest todoWriteRequest) {
        Object memberId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        todoService.todoWriteProcess(todoWriteRequest, memberId);
        return new ResponseMessage(HttpStatus.OK, "success");
    }

    @Parameter(description = "로그인 회원의 accessToken", name = "ACCESS_TOKEN", in = ParameterIn.HEADER)
    @Operation(summary = "TODO 수정", description = "TODO를 수정 할 수 있다.")
    @PutMapping(value = "/{todoId}")
    public ResponseMessage todoStatusModify(@PathVariable(value = "todoId")Long todoId,
                                            @RequestParam(value = "todoStatus")TodoStatus todoStatus) {
        Object memberId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        todoService.todoStatusModifyProcess(todoStatus, memberId,todoId);
        return new ResponseMessage(HttpStatus.OK, "success");
    }

    @Parameter(description = "로그인 회원의 accessToken", name = "ACCESS_TOKEN", in = ParameterIn.HEADER)
    @Operation(summary = "TODO 조회", description = "TODO를 조회 할 수 있다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = TodoListResponse.class)))
    @GetMapping(value = "/list")
    public ResponseDataMessage todoList(@RequestParam(value = "size") int size,
                                        @RequestParam(value = "page") int page,
                                        @RequestParam(value = "properties") PagingProperties properties) {
        Object memberId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TodoListResponse> todoListResponse =
                todoService.todoListProcess(PageRequest.of(page, size, Sort.Direction.DESC, properties.getProperties()), memberId);
        return new ResponseDataMessage(HttpStatus.OK, "success", todoListResponse);
    }

}
