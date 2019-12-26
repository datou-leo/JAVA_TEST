package com.example.aopdemo;

import com.example.aopdemo.model.Coffee;
import com.example.aopdemo.model.CoffeeOrder;
import com.example.aopdemo.model.OrderState;
import com.example.aopdemo.repository.CoffeeRepository;
import com.example.aopdemo.service.CoffeeOrderService;
import com.example.aopdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class AopdemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Autowired
	private CoffeeRepository coffeeRepository;

	@Autowired
	private CoffeeService coffeeService;

	@Autowired
	private CoffeeOrderService coffeeOrderService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("All Coffee:{}",coffeeRepository.findAll());
		Optional<Coffee> capuccino = coffeeService.findOneCofee("capuccino");
		if(capuccino.isPresent()){
			log.info("capuccino:{}",capuccino.get().toString());
			CoffeeOrder order=coffeeOrderService.createOrder("hanmeimei",capuccino.get());
			log.info("Update INIT to PAID:{}",coffeeOrderService.updateState(order, OrderState.PAID));
			log.info("Update INIT to INIT:{}",coffeeOrderService.updateState(order, OrderState.INIT));
		}
	}

}
