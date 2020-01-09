package com.example.eurekaspringbuckswater.repository;

import com.example.eurekaspringbuckswater.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {

}
