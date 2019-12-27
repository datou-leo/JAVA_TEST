package com.example.mvcpluscontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@EnableJpaRepositories
@SpringBootApplication
public class MvcpluscontrollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcpluscontrollerApplication.class, args);
	}

}
