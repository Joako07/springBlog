package com.springboot.blog.springboot_blog.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
public class BlogAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus state;
    private String mesagge; 

}
