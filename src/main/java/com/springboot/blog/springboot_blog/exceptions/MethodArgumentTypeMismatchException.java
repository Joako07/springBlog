package com.springboot.blog.springboot_blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

import java.io.Serial;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MethodArgumentTypeMismatchException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus state;
    private String mesagge; 

}
