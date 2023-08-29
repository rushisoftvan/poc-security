package com.softvan.config;


import com.softvan.jwt.JwtTokenFilter;
import com.softvan.jwt.JwtTokenFilterConfigurer;
import com.softvan.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class    SecurityConfig {


    private final AccessDeniedHandler accessDeniedHandler;
   private final JwtTokenProvider jwtProvider;



    private final JwtTokenFilter jwtFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

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
        http.cors();
        http.authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
//                .antMatchers("/m1").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                //.addFilterBefore(new JwtTokenFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

        /**
         * We have to set this authenticationEntryPoint otherwise we will get 403 instead of 401 if login cred invalid
         */

        http.exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler);

        /**
         * JwtFilter -> UsernamePasswordAuthenticationFilter
         * SecurityContext (Authentication(Valid user))
         */

        //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.apply(new JwtTokenFilterConfigurer(jwtProvider));

        return http.build();
    }


}