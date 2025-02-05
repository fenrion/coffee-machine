package com.aisa.test.repository;

import com.aisa.test.domain.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Optional<Coffee> findById(Long id);
}
