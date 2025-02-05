package com.aisa.test.controller.impl;

import com.aisa.test.controller.StatisticsController;
import com.aisa.test.domain.dto.StatisticsAllDto;
import com.aisa.test.domain.dto.StatisticsByCoffeeDto;
import com.aisa.test.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StatisticsControllerImpl implements StatisticsController {

    private final StatisticsService statisticsService;

    @Override
    public StatisticsAllDto getAllStatistics() {
        return statisticsService.getAllStatistics();
    }

    @Override
    public StatisticsByCoffeeDto getStatisticsByCoffee(Long coffeeID) {
        return statisticsService.getStatisticsByCoffee(coffeeID);
    }
}
