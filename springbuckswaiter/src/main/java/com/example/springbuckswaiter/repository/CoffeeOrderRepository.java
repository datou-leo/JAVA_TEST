package com.example.springbuckswaiter.repository;

import com.example.springbuckswaiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {

}
