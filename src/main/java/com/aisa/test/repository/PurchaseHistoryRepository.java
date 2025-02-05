package com.aisa.test.repository;

import com.aisa.test.domain.model.Coffee;
import com.aisa.test.domain.model.PurchaseHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, UUID> {
    List<PurchaseHistory> findByCoffee(Coffee coffee);
//    @Query("SELECT ph FROM PurchaseHistory ph JOIN FETCH ph.coffee")    //для решения N+1 с помощью JOIN FETCH
//    List<PurchaseHistory> findAllWithCoffee();
    @EntityGraph(attributePaths = {"coffee"}, type = EntityGraph.EntityGraphType.FETCH)
    List<PurchaseHistory> findAll();
}
