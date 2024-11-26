package com.springboot.blog.springboot_blog.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiExceptionResponse {

    private Date timeStamp;
    private String message;
    private String details;

}
