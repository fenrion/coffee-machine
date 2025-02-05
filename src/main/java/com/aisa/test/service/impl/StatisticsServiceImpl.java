package com.aisa.test.service.impl;

import com.aisa.test.domain.dto.StatisticsAllDto;
import com.aisa.test.domain.dto.StatisticsByCoffeeDto;
import com.aisa.test.domain.exception.NotFoundException;
import com.aisa.test.domain.model.Coffee;
import com.aisa.test.domain.model.PurchaseHistory;
import com.aisa.test.repository.CoffeeRepository;
import com.aisa.test.repository.PurchaseHistoryRepository;
import com.aisa.test.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CoffeeRepository coffeeRepository;

    @Override
    @Transactional
    public StatisticsAllDto getAllStatistics() {
        log.info("Поиск статистики по покупкам каждого вида кофе");
        //List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAllWithCoffee(); //для решения N+1 с помощью JOIN FETCH
        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAll();
        Map<String, Long> stats = purchaseHistories.stream()
                .collect(Collectors.groupingBy(
                        purchaseHistory -> purchaseHistory.getCoffee().getName(),
                        Collectors.counting()));
        return new StatisticsAllDto(stats);
    }

    @Override
    @Transactional
    public StatisticsByCoffeeDto getStatisticsByCoffee(Long coffeeID) {
        log.info("Поиск кофе c id {}", coffeeID);
        Coffee coffee = coffeeRepository.findById(coffeeID)
                .orElseThrow(() -> new NotFoundException("Кофе с таким id не найден"));
        log.info("Поиск покупок кофе с id {}", coffeeID);
        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findByCoffee(coffee);
        StatisticsByCoffeeDto statistics = new StatisticsByCoffeeDto(
                coffee.getName(),
                purchaseHistories.size(),
                purchaseHistories.size()*coffee.getPrice()
        );
        return statistics;
    }
}
