package com.assignment.api.todo.dto.request;

import com.assignment.core.domain.entity.value.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoWriteRequest {

    @NotEmpty(message = "내용이 입력되지 않았습니다.")
    private String content;

    @NotNull(message = "상태값이 입력되지 않았습니다.")
    private TodoStatus todoStatus;
}
