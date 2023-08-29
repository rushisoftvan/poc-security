package com.softvan.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.annotation.PostConstruct;

@Getter
@Slf4j
@Setter
@RequiredArgsConstructor
public class JwtProperties {


    private   String jwtExpireTimeInMinute;

    private final  String jwtSecretKey;


    @PostConstruct
    public void inIt(){

    }
}
