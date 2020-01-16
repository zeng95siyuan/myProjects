package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"WebSocket"})
public class ChatExperimentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatExperimentApplication.class, args);
	}

}
