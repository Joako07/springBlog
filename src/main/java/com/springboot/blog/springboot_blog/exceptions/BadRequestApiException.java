package com.springboot.blog.springboot_blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestApiException extends RuntimeException{

    private final String error;
    private final String message;

    public String getError() {
        return error;
    }
}
