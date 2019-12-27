package com.example.mvccontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@EnableJpaRepositories
@SpringBootApplication
public class MvccontrollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvccontrollerApplication.class, args);
	}

}
