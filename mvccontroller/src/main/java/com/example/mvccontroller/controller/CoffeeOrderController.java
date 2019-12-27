package com.example.mvccontroller.controller;

import com.example.mvccontroller.controller.request.NewOrderRequest;
import com.example.mvccontroller.model.Coffee;
import com.example.mvccontroller.model.CoffeeOrder;
import com.example.mvccontroller.service.CoffeeOrderService;
import com.example.mvccontroller.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    
    @Autowired
    private CoffeeService coffeeService;
    
    @Autowired
    private CoffeeOrderService coffeeOrderService;


    @GetMapping(path = "/" ,params = "id")
    @ResponseBody
    public CoffeeOrder getById(
            @RequestParam
            Long id
    ){
        return coffeeOrderService.getOrderById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(
            @RequestBody NewOrderRequest newOrder
    ){
        log.info("Receive new Order{}",newOrder);

        Coffee[] coffeeList=coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});

        return coffeeOrderService.createOrder(newOrder.getCustomer(),coffeeList);
    }
}
