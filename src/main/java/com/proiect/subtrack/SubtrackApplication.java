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

// Clear Redis cache if data appears stale after DB changes:
// docker exec -it subtrack-redis redis-cli FLUSHALL

// Cache TTL: 2 minutes (120000ms)
// Cache is automatically cleared on CREATE/UPDATE/DELETE operations
