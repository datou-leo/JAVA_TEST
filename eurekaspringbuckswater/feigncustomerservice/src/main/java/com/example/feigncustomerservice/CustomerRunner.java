package com.example.feigncustomerservice;

import com.example.feigncustomerservice.integration.CoffeeOrderService;
import com.example.feigncustomerservice.integration.CoffeeService;
import com.example.feigncustomerservice.model.Coffee;
import com.example.feigncustomerservice.model.CoffeeOrder;
import com.example.feigncustomerservice.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readMenu();
        Long id =orderCoffee();
        queryOrder(id);
    }

    private void readMenu(){
        List<Coffee> list=coffeeService.getAll();
        list.forEach(c->log.info("Coffee: {}:",c));
    }


    private Long orderCoffee(){
        NewOrderRequest orderRequest=NewOrderRequest.builder().customer("hanmeimei").items(Arrays.asList("mocha")).build();
        CoffeeOrder coffeeOrder=coffeeOrderService.create(orderRequest);
        log.info("Order Id :{}",coffeeOrder.getId());
        Long id = coffeeOrder.getId();
        return id;
    }

    private void queryOrder(Long id){
        CoffeeOrder order =coffeeOrderService.getOrder(id);
        log.info("Order:{}",order);
    }
}
