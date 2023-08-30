package com.softvan.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Getter
@Slf4j
@Setter
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {


    private  String jwtExpireTimeInMinute="5";

    private   String jwtSecretKey;


    @PostConstruct
    public void inIt(){
        log.info("jwt been is created");
    }

}
