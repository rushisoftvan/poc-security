package com.softvan.config;


import com.softvan.jwt.JwtFilter;
import com.softvan.jwt.JwtTokenFilterConfigurer;
import com.softvan.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;

//    private final JwtTokenFilterConfigurer jwtTokenFilterConfigurer;

    private final AccessDeniedHandler accessDeniedHandler;

    private final JwtFilter jwtFilter;

    public static String[] PUBLIC_URLS = {
            "/login"
//                "/api/users/**",
//                "/api/auth/**",
//                "/v3/api-docs/**",
//                "/swagger-ui/**",
//                "/swagger-ui.html",
//                "/claims/test/**",
//                "/favicon.ico"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        /**
         * Need to disable csrf otherwise we will get 403 or 401
         * on public POST types urls
         */
        http
                .csrf()
                .disable();
//        http.cors();
        http.authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
//                .antMatchers("/m1").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

        /**
         * We have to set this authenticationEntryPoint otherwise we will get 403 instead of 401 if login cred invalid
         */

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        /**
         * JwtFilter -> UsernamePasswordAuthenticationFilter
         * SecurityContext (Authentication(Valid user))
         */

        //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.apply(jwtTokenFilterConfigurer);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}