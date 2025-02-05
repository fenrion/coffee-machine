package com.aisa.test.service;

import com.aisa.test.domain.dto.StatisticsAllDto;
import com.aisa.test.domain.dto.StatisticsByCoffeeDto;
import com.aisa.test.domain.exception.NotFoundException;
import com.aisa.test.domain.model.Coffee;
import com.aisa.test.domain.model.PurchaseHistory;
import com.aisa.test.repository.CoffeeRepository;
import com.aisa.test.repository.PurchaseHistoryRepository;
import com.aisa.test.service.impl.CoffeeServiceImpl;
import com.aisa.test.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceImplTest {
    @Mock
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Mock
    CoffeeRepository coffeeRepository;

    StatisticsService statisticsService;
    private Coffee coffee;

    @BeforeEach
    void init() {
        statisticsService = new StatisticsServiceImpl(
                purchaseHistoryRepository, coffeeRepository);

        coffee = new Coffee()
                .setId(1L)
                .setName("Latte")
                .setMilk(200)
                .setBoiledWater(100)
                .setCoffeeBeans(20)
                .setPrice(3.5);
    }

    @Test
    public void getAllStatistics_ReturnsStatisticsAllDtoTest() {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        PurchaseHistory purchaseHistory1 = new PurchaseHistory()
                .setId(UUID.randomUUID())
                .setCoffee(coffee)
                .setOperationDate(LocalDate.now());
        PurchaseHistory purchaseHistory2 = new PurchaseHistory()
                .setId(UUID.randomUUID())
                .setCoffee(coffee)
                .setOperationDate(LocalDate.now());
        purchaseHistories.add(purchaseHistory1);
        purchaseHistories.add(purchaseHistory2);

        when(purchaseHistoryRepository.findAll()).thenReturn(purchaseHistories);

        StatisticsAllDto statisticsAllDto = statisticsService.getAllStatistics();

        assertNotNull(statisticsAllDto);
        assertEquals(1, statisticsAllDto.stats().size());
        assertEquals(2L, statisticsAllDto.stats().get("Latte"));
        verify(purchaseHistoryRepository, times(1)).findAll();
    }

    @Test
    public void getStatisticsByCoffee_ExistingCoffeeId_ReturnsStatisticsByCoffeeDtoTest() {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        PurchaseHistory purchaseHistory1 = new PurchaseHistory()
                .setId(UUID.randomUUID())
                .setCoffee(coffee)
                .setOperationDate(LocalDate.now());
        purchaseHistories.add(purchaseHistory1);

        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));
        when(purchaseHistoryRepository.findByCoffee(coffee)).thenReturn(purchaseHistories);

        StatisticsByCoffeeDto statisticsByCoffeeDto = statisticsService.getStatisticsByCoffee(1L);

        assertNotNull(statisticsByCoffeeDto);
        assertEquals("Latte", statisticsByCoffeeDto.name());
        assertEquals(1, statisticsByCoffeeDto.numberOfPurchase());
        assertEquals(3.5, statisticsByCoffeeDto.money());
        verify(coffeeRepository, times(1)).findById(1L);
        verify(purchaseHistoryRepository, times(1)).findByCoffee(coffee);
    }

    @Test
    public void getStatisticsByCoffee_NonExistingCoffeeId_ThrowsNotFoundException() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> statisticsService.getStatisticsByCoffee(1L));
        verify(coffeeRepository, times(1)).findById(1L);
        verify(purchaseHistoryRepository, never()).findByCoffee(any());
    }
}