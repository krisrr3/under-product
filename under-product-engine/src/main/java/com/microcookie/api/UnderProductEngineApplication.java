package com.microcookie.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.microcookie.api")
public class UnderProductEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnderProductEngineApplication.class, args);
	}

}
