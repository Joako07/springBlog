package com.springboot.blog.springboot_blog.exceptions;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiExceptionResponse {

    private Instant timeStamp;
    private String message;
    private String details;

}
