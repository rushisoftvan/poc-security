package com.softvan.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate","Basic realm=\"Realm\"");
        log.info("<<<<<<<<<<<<<AccessDeniedHandlerImpl");
        this.handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
        log.info("AccessDeniedHandlerImpl>>>>>>>>>>>");
    }
}
