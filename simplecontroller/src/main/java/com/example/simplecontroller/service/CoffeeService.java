package com.example.simplecontroller.service;

import com.example.simplecontroller.model.Coffee;
import com.example.simplecontroller.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@CacheConfig(cacheNames = "CoffeeCache")
@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;


    public Optional<Coffee> findOneCofee(String name){
        ExampleMatcher matcher=ExampleMatcher.matching().withMatcher("name",exact().ignoreCase());
        Optional<Coffee> coffee =coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(),matcher));
        log.info("Coffee Found:{}",coffee);
        return coffee;
    }

    @Cacheable
    public List<Coffee> getAllCoffee(){
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public List<Coffee> getCoffeeByName(List<String> names){
        return coffeeRepository.findByNameInOrderById(names);
    }
}
