package com.example.springbuckswaiter.service;

import com.example.springbuckswaiter.model.Coffee;
import com.example.springbuckswaiter.model.CoffeeOrder;
import com.example.springbuckswaiter.model.OrderState;
import com.example.springbuckswaiter.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {



    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder createOrder(String customer, Coffee... coffee) {
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(new ArrayList<>(Arrays.asList(coffee))).state(OrderState.INIT).build();

        log.info("Order:{}",order);
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("New Order:{}", saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState orderState) {
        if (orderState.compareTo(order.getState()) < 0){
            log.warn("Wrong State order:{},{}", orderState, order.getState());
            return false;
        }
        order.setState(orderState);

        coffeeOrderRepository.save(order);
        log.info("Update Order:{}",order);
        return true;
    }

    public CoffeeOrder getOrderById(Long id){
        return coffeeOrderRepository.findById(id).get();
    }



}
