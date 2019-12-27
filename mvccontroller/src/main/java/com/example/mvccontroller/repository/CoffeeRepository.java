package com.example.mvccontroller.repository;

import com.example.mvccontroller.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
        List<Coffee> findByNameInOrderById(List<String> list);
}
