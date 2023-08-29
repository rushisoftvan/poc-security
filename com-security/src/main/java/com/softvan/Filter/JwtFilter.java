//package com.softvan.Filter;
//
//import com.softvan.jwt.JwtProvider;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.websocket.OnClose;
//import java.io.IOException;
//
//
//@Slf4j
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtProvider jwtProvider;
//
//    public JwtFilter(JwtProvider jwtProvider){
//        this.jwtProvider=jwtProvider;
//    };
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("doFilterInternal start ");
//        String token = request.getHeader("Authorization");
//        if (token != null) {
//            String accessToken = token.split(" ")[1];
//
//          if(this.jwtProvider.validateToken(accessToken)){
//              Authentication auth = jwtProvider.getAuthentication(token);
//              System.out.println("auth = " + auth);
//              SecurityContextHolder.getContext().setAuthentication(auth);
//          }
//         filterChain.doFilter(request,response);
//        }
//        filterChain.doFilter(request,response);
//    }
//}
