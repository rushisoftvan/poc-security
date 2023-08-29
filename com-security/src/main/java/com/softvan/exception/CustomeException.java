package com.softvan.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
public class CustomeException extends RuntimeException{

    private  String msg;
    private  HttpStatus status;
    public CustomeException(String msg,HttpStatus status){
       super(msg);
        this.status=status;
    }


}
