package com.aisa.test.repository;

import com.aisa.test.domain.model.Coffee;
import com.aisa.test.domain.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, UUID> {
    List<PurchaseHistory> findByCoffee(Coffee coffee);
}
