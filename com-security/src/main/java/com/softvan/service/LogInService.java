package com.softvan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softvan.Repository.UserRepsitory;
import com.softvan.dto.request.LoginRequestDto;
import com.softvan.dto.response.TokenResponse;
import com.softvan.entity.UserEntity;
import com.softvan.exception.CustomeException;

import com.softvan.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LogInService {

    private final UserRepsitory userRepsitory;

    private final PasswordEncoder passwordEncoder;
    private  final JwtTokenProvider jwtTokenProvider;

    public LogInService(UserRepsitory userRepsitory, PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {
        this.userRepsitory = userRepsitory;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
    }


//


    public String createJwtToken(LoginRequestDto loginRequestDto) throws JsonProcessingException {

        UserEntity user = this.userRepsitory.getUserByUsername(loginRequestDto.getEmail()).orElseThrow(() -> new CustomeException("User Not Found For This Username",HttpStatus.BAD_REQUEST));
        String role = user.getRole().getName();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomeException("password is not correct", HttpStatus.BAD_REQUEST);
        }
        return this.jwtTokenProvider.createToken(user.getEmail(), user.getId(), role);
    }


}
