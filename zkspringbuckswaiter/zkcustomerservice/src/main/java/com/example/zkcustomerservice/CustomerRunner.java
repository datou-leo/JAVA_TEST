package com.example.zkcustomerservice;

import com.example.zkcustomerservice.model.Coffee;
import com.example.zkcustomerservice.model.CoffeeOrder;
import com.example.zkcustomerservice.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
        showServiceInstance();
        readMenu();
        Long id =orderCoffee();
        queryOrder(id);
    }

    private void showServiceInstance(){
        log.info("DiscoveryClient:{}",discoveryClient);
        discoveryClient.getInstances("waiter-service").forEach(s->{
            log.info("Host:{},Port:{},",s.getHost(),s.getPort());
        });
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    private void readMenu(){
        String coffeeUri="http://waiter-service/coffee/";

        ParameterizedTypeReference<List<Coffee>> parameterizedTypeReference=new ParameterizedTypeReference<List<Coffee>>() {};

        ResponseEntity<List<Coffee>> list=restTemplate.exchange(coffeeUri, HttpMethod.GET,null,parameterizedTypeReference);

        list.getBody().forEach(c->log.info("Coffee: {}:",c));
    }


    private Long orderCoffee(){
        NewOrderRequest orderRequest=NewOrderRequest.builder().customer("hanmeimei").items(Arrays.asList("mocha")).build();
        RequestEntity<NewOrderRequest> request=RequestEntity.post(UriComponentsBuilder
                .fromUriString("http://waiter-service/order/").build().toUri()).body(orderRequest);
        ResponseEntity<CoffeeOrder> response=restTemplate.exchange(request,CoffeeOrder.class);
        log.info("Order Request Status Code:{}",response.getStatusCode());
        Long id = response.getBody().getId();
        log.info("body :{}",response.getBody());
        log.info("Order Id :{}",id);
        return id;
    }

    private void queryOrder(Long id){
        CoffeeOrder order = restTemplate.getForObject("http://waiter-service/order/?id={id}",CoffeeOrder.class,id);
        log.info("Order:{}",order);
    }
}
