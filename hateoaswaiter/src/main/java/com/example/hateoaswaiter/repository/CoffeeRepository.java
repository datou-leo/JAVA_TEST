package com.example.hateoaswaiter.repository;

import com.example.hateoaswaiter.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path="coffee")
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
}
