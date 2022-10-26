package com.assignment.api.config.message;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Tag(name = "응답객체", description = "응답 메세지 및 코드")
@Getter
public class ResponseMessage {

    private String message;

    private HttpStatus status;

    public ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
