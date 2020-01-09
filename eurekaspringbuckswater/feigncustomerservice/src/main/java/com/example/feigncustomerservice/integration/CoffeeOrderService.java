package com.example.feigncustomerservice.integration;

import com.example.feigncustomerservice.model.CoffeeOrder;
import com.example.feigncustomerservice.model.NewOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="waiter-service",contextId ="coffeeOrder")
public interface CoffeeOrderService {

        @GetMapping("/order/?id={id}")
        CoffeeOrder getOrder(@RequestParam
                                     Long id);

        @PostMapping(path="/order/",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        CoffeeOrder create(@RequestBody NewOrderRequest newOrder);
}
