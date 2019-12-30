package com.example.springbuckscustomer;

import com.example.springbuckscustomer.model.Coffee;
import com.example.springbuckscustomer.model.CoffeeOrder;
import com.example.springbuckscustomer.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readMenu();
        Long id =orderCoffee();
        queryOrder(id);
    }

    private void readMenu(){
        String coffeeUri="http://localhost:8080/coffee/";

        ParameterizedTypeReference<List<Coffee>> parameterizedTypeReference=new ParameterizedTypeReference<List<Coffee>>() {};

        ResponseEntity<List<Coffee>> list=restTemplate.exchange(coffeeUri, HttpMethod.GET,null,parameterizedTypeReference);

        list.getBody().forEach(c->log.info("Coffee: {}:",c));
    }


    private Long orderCoffee(){
        NewOrderRequest orderRequest=NewOrderRequest.builder().customer("hanmeimei").items(Arrays.asList("mocha")).build();
        RequestEntity<NewOrderRequest> request=RequestEntity.post(UriComponentsBuilder
                .fromUriString("http://localhost:8080/order/").build().toUri()).body(orderRequest);
        ResponseEntity<CoffeeOrder> response=restTemplate.exchange(request,CoffeeOrder.class);
        log.info("Order Request Status Code:{}",response.getStatusCode());
        Long id = response.getBody().getId();
        log.info("body :{}",response.getBody());
        log.info("Order Id :{}",id);
        return id;
    }

    private void queryOrder(Long id){
        CoffeeOrder order = restTemplate.getForObject("http://localhost:8080/order/?id={id}",CoffeeOrder.class,id);
        log.info("Order:{}",order);
    }
}
