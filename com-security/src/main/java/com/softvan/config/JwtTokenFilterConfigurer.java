//package com.softvan.config;
//
//
//import com.softvan.jwt.JwtTokenFilter;
//import com.softvan.jwt.JwtTokenProvider;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//
//public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    //private final JwtTokenProvider jwtProvider;
//    //rivate final JwtFilter jwtFilter;
//    private final JwtTokenProvider jwtTokenProvider;
//
//
//    public JwtTokenFilterConfigurer(JwtTokenProvider jwtProvider){
//        this.jwtTokenProvider=jwtProvider;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        JwtTokenFilter jwtFilter = new JwtTokenFilter(jwtTokenProvider);
//        http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}
