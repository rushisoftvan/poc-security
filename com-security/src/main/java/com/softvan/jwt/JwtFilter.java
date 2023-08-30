package com.softvan.jwt;

import com.softvan.config.SecurityConfig;
import com.softvan.exception.CustomLoginException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {



    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token";


//    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//    public JwtFilter(HandlerExceptionResolver exceptionResolver){
//        this.exceptionResolver=exceptionResolver;
//    }


//    private void setHeader(HttpServletResponse response, String newToken) {
//        response.setHeader("Authorization", newToken);
//        response.setHeader("Access-Control-Expose-Headers", "Authorization");
//    }

//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
//        log.info("<<<<<< do filter");
//        try {
//            HttpServletResponse response = (HttpServletResponse) res;
//            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
//            System.out.println("token = " + token);
//
//            if ((token != null && !token.isEmpty())) {
//                try {
//                    jwtTokenProvider.validateToken(token);
//                } catch (JwtException | IllegalArgumentException e) {
//                    e.printStackTrace();
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, INVALID_JWT_TOKEN);
//                    throw new RuntimeException("invalid token");
//                }
//                Authentication auth = jwtTokenProvider.getAuthentication(token);
//                System.out.println("auth = " + auth);
//
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//                //setHeader(response, jwtTokenProvider.createNewToken(token));
//
//            }
//            filterChain.doFilter(req, res);
//
//        } catch (Exception e) {
//            log.error("error : {}", e);
//            throw e;
//        }
//    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return stream(SecurityConfig.PUBLIC_URLS)
//                .anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("<<<<<< JwtFilter");
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if (!StringUtils.isBlank(token)){
                log.info("TOKEN :: {}", token);

                if ((token != null && !token.isEmpty())) {
                    jwtTokenProvider.validateToken(token);
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    log.info("Authentication object :: {}" , authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //setHeader(response, jwtTokenProvider.createNewToken(token));
                }
            }

            filterChain.doFilter(request, response);

            log.info("JwtFilter >>>>>>");

        } catch (JwtException ex) {
            exceptionResolver.resolveException(request,response, null , ex);
        }
    }


//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return stream(SecurityConfig.PUBLIC_URLS)
//                .anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
//    }

}
