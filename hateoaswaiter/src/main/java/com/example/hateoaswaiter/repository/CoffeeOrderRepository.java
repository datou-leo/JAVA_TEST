package com.example.hateoaswaiter.repository;

import com.example.hateoaswaiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {

}
