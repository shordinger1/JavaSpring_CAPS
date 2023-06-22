package com.team4.caps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })
public class CapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapsApplication.class, args);
	}

}
