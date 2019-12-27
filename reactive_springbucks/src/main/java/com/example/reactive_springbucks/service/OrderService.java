package com.example.reactive_springbucks.service;

import com.example.reactive_springbucks.model.CoffeeOrder;
import com.example.reactive_springbucks.repository.CoffeeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public Mono<Long> create(CoffeeOrder order){

        return coffeeOrderRepository.save(order);
    }
}
