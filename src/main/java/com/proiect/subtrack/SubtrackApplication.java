package com.proiect.subtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SubtrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubtrackApplication.class, args);
	}

}
//http://localhost:8080/swagger-ui/index.html