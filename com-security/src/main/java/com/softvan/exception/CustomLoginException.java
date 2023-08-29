package com.softvan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.UNAUTHORIZED)
public class CustomLoginException extends RuntimeException {
        public CustomLoginException(String msg){
            super(msg);
        }
}
