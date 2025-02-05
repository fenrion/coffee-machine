package com.aisa.test.service;

import com.aisa.test.domain.dto.StatisticsAllDto;
import com.aisa.test.domain.dto.StatisticsByCoffeeDto;

import java.util.List;

public interface StatisticsService {
    StatisticsAllDto getAllStatistics();
    StatisticsByCoffeeDto getStatisticsByCoffee(Long coffeeID);
}
