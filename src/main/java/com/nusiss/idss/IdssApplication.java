package com.nusiss.idss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class IdssApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdssApplication.class, args);
	}

}
