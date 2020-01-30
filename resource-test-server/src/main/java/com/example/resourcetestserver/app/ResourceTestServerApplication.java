package com.example.resourcetestserver.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.resourcetestserver")
public class ResourceTestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceTestServerApplication.class, args);
	}

}
