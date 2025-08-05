package com.cleandriver.controller.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cleandriver.persistence") // <-- Reemplazá con el paquete real
@EntityScan(basePackages = "com.cleandriver.model") // <-- Reemplazá con el paquete real
public class ClenaDriverAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClenaDriverAppApplication.class, args);
	}

}
