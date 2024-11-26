package com.springboot.blog.springboot_blog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApiErrorException extends RuntimeException {

    private final String error;
    private final String message;

}
