package com.example.consulspringbuckswaiter.repository;

import com.example.consulspringbuckswaiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {

}
