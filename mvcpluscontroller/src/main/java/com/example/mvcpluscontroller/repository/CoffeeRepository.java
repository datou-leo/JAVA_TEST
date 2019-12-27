package com.example.mvcpluscontroller.repository;

import com.example.mvcpluscontroller.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
        List<Coffee> findByNameInOrderById(List<String> list);
}
