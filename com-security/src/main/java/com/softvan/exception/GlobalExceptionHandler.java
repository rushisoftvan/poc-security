package com.softvan.exception;

import com.softvan.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String INVALID_TOKEN_MSG = "Invalid Token";
    public static final String EXCEPTION_OCCURRED_MSG = "Exception occurred ";
    public static final String ACCESS_DENIED_EXCEPTION_MSG = "You don't have access to this resource, Please contact to admin";

    @ExceptionHandler(CustomLoginException.class)
    public ErrorResponse CustomLoginException(CustomLoginException ex){
        logException(ex);
        return new ErrorResponse(ex.getMessage() , HttpStatus.BAD_REQUEST.value());

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException ex){
        logException(ex);
        return new ErrorResponse("Token is expired" , HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex){
        logException(ex);
        return new ErrorResponse("PLease provide valid credentials", HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex){
        logException(ex);
        return new ErrorResponse(ACCESS_DENIED_EXCEPTION_MSG, HttpStatus.FORBIDDEN.value());

    }

    private static void logException(Exception ex) {
        log.error(EXCEPTION_OCCURRED_MSG , ex);
    }

    @ExceptionHandler(JwtException.class)
    public ErrorResponse handleJwtException(JwtException ex){
        logException(ex);
        return new ErrorResponse(INVALID_TOKEN_MSG, HttpStatus.UNAUTHORIZED.value());
    }


}
