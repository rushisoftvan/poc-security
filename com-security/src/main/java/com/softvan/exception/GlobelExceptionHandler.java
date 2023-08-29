package com.softvan.exception;

import com.softvan.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobelExceptionHandler {

    @ExceptionHandler(CustomLoginException.class)
    public ApiResponse<String> CustomLoginException(CustomLoginException customLoginException){
       return new ApiResponse(customLoginException.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }
}
