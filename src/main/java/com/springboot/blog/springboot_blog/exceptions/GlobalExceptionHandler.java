package com.springboot.blog.springboot_blog.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(Instant.now())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    } 

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ApiExceptionResponse> handleBlogAppException(BlogAppException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(Instant.now())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiExceptionResponse> handleGlobalException(ApiErrorException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(Instant.now())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiExceptionResponse> handleMethodArgumentTypeException(MethodArgumentTypeMismatchException ex, WebRequest webRequest){
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(Instant.now())
        .message("Tipo de dato incorrecto para el parámetro solicitado")
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestApiException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(BadRequestApiException ex, WebRequest webRequest) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(Instant.now())
        .message(ex.getMessage())
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    //Este metodo lo obtengo de la extencion ResponseEntityExceptionHandler y es para manejar la validación de argumentos en los controladores
    @SuppressWarnings("null")
    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
       //Creo el map para devolver el en el json
       Map<String, String> errors = new HashMap<>();

       //Traigo todos los errores y los recorro uno por uno 
       ex.getBindingResult().getAllErrors().forEach((error) ->{
        //Casteo el error a un FieldError y obtengo el nombre del campo
        String fieldName = ((FieldError)error).getField();
        //Obtengo el mensaje del error
        String message = error.getDefaultMessage();

        errors.put(fieldName, message);
       });
       return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

}
