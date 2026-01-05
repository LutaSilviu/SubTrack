package com.proiect.subtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class SubtrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubtrackApplication.class, args);
	}

}
//http://localhost:8080/swagger-ui/index.html

// Using simple in-memory cache (ConcurrentMapCacheManager)
// Cache is automatically cleared on application restart
// Cache is automatically cleared on CREATE/UPDATE/DELETE operations
