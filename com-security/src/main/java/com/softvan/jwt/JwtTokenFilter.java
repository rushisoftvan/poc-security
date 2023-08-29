package com.softvan.jwt;

import com.softvan.config.SecurityConfig;
import com.softvan.exception.CustomLoginException;
import com.softvan.exception.CustomeException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.stream;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {


      @Autowired
      @Qualifier("handlerExceptionResolver")
    private  HandlerExceptionResolver exceptionResolver;
    private  JwtTokenProvider jwtTokenProvider;

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token";


    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
//    public JwtTokenFilter(HandlerExceptionResolver exceptionResolver){
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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return stream(SecurityConfig.PUBLIC_URLS)
                .anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        log.info("<<<<<< do filter");
        try {
            HttpServletResponse response = (HttpServletResponse) res;
            if (req.getHeader("Authorization") == null) {

                throw new CustomLoginException("please login");

            }
            ;
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            System.out.println("token = " + token);

            if ((token != null && !token.isEmpty())) {
                try {
                    jwtTokenProvider.validateToken(token);
                } catch (JwtException | IllegalArgumentException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, INVALID_JWT_TOKEN);
                    throw new RuntimeException("invalid token");
                }

                Authentication auth = jwtTokenProvider.getAuthentication(token);
                System.out.println("auth = " + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);

                //setHeader(response, jwtTokenProvider.createNewToken(token));
            }

            filterChain.doFilter(req, res);

        } catch (CustomLoginException customLoginException) {
            log.error("Spring Security Filter Chain Exception: {}", customLoginException);
            exceptionResolver.resolveException(req, res, null, customLoginException);
            //this.exceptionResolver.resolveException(req, res, null, customLoginException);
        }
         catch (Exception e) {
            log.error("error : {}", e);
            throw e;
        }
    }


//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return stream(SecurityConfig.PUBLIC_URLS)
//                .anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
//    }

}
