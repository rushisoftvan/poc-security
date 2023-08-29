package com.softvan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softvan.dto.request.LoginRequestDto;
import com.softvan.dto.response.ApiResponse;
import com.softvan.dto.response.TokenResponse;
import com.softvan.service.LogInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

@Slf4j
public class UserController {

private final LogInService logInService;

public UserController(LogInService logInService){
    this.logInService=logInService;

}





    @PostMapping("/login")
    public ApiResponse logIn(@RequestBody LoginRequestDto loginRequestDto) throws JsonProcessingException {
       log.info("email {}", loginRequestDto.getEmail());
        String jwtToken = this.logInService.createJwtToken(loginRequestDto);
        TokenResponse tokenResponse = new TokenResponse(jwtToken);
        log.info("token {}", jwtToken);
        return new ApiResponse(tokenResponse, HttpStatus.OK.value());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/m1")
    public String m1() {
        return  "Authorized";
        //log.info("sachin");
    }



}
