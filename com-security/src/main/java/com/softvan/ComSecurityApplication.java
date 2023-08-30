package com.softvan;

import com.softvan.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {JwtProperties.class})
public class ComSecurityApplication  implements CommandLineRunner {

	private final JwtProperties jwtProperties;

	public static void main(String[] args) {
		SpringApplication.run(ComSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		  log.info("jwtdate : {}",jwtProperties.getJwtExpireTimeInMinute());
		  log.info("jwtkey : {} ",jwtProperties.getJwtSecretKey());
	}
}
