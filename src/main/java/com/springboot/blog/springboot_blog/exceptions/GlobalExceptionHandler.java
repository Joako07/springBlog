package com.springboot.blog.springboot_blog.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ResponseEntity<ApiExceptionResponse> handlerNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(new Date())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    } 

    @ExceptionHandler(BlogAppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiExceptionResponse> handlerBlogAppException(BlogAppException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(new Date())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiExceptionResponse> handlerGlobalException(ApiErrorException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(new Date())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiExceptionResponse> handlerMethodArgumentTypeException(MethodArgumentTypeMismatchException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(new Date())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.BAD_REQUEST);
    }

}
