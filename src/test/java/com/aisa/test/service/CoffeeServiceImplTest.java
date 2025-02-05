package com.aisa.test.service;

import com.aisa.test.domain.dto.AmountDto;
import com.aisa.test.domain.dto.CoffeeAllDto;
import com.aisa.test.domain.dto.CoffeeDto;
import com.aisa.test.domain.dto.CoffeeIdDto;
import com.aisa.test.domain.exception.NotEnoughFundsException;
import com.aisa.test.domain.exception.NotEnoughIngredientsException;
import com.aisa.test.domain.exception.NotFoundException;
import com.aisa.test.domain.mapper.CoffeeMapper;
import com.aisa.test.domain.model.Coffee;
import com.aisa.test.domain.model.Storage;
import com.aisa.test.repository.CoffeeRepository;
import com.aisa.test.repository.PurchaseHistoryRepository;
import com.aisa.test.repository.StorageRepository;
import com.aisa.test.service.impl.CoffeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoffeeServiceImplTest {
    @Mock
    CoffeeRepository coffeeRepository;
    @Mock
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Mock
    StorageRepository storageRepository;
    @Mock
    CoffeeMapper coffeeMapper;

    CoffeeService coffeeService;
    private Coffee coffee;
    private CoffeeDto coffeeDto;
    private Storage storage;
    private AmountDto amountDto;

    @BeforeEach
    void init() {
        coffeeService = new CoffeeServiceImpl(
                coffeeRepository, purchaseHistoryRepository, storageRepository, coffeeMapper);

        coffee = new Coffee()
                .setId(1L)
                .setName("Latte")
                .setMilk(200)
                .setBoiledWater(100)
                .setCoffeeBeans(20)
                .setPrice(100.0);

        coffeeDto = new CoffeeDto(
                "Latte",
                200,
                100,
                20,
                100.0
        );

        storage = new Storage()
                .setId(UUID.randomUUID())
                .setMilk(500)
                .setBoiledWater(500)
                .setCoffeeBeans(100);

        amountDto = new AmountDto(150.0);
    }

    @Test
    void getCoffeeListTest() {
        List<Coffee> coffeeList = new ArrayList<>();
        coffeeList.add(coffee);
        CoffeeAllDto coffeeAllDto = new CoffeeAllDto(1L,"Latte", 100.00);

        when(coffeeRepository.findAll()).thenReturn(coffeeList);
        when(coffeeMapper.coffeeToCoffeeAllDto(coffee)).thenReturn(coffeeAllDto);

        List<CoffeeAllDto> coffeeAllDtoList = coffeeService.getCoffeeList();

        assertEquals(1, coffeeAllDtoList.size());
        assertEquals("Latte", coffeeAllDtoList.get(0).name());
        verify(coffeeRepository, times(1)).findAll();
        verify(coffeeMapper, times(1)).coffeeToCoffeeAllDto(coffee);
    }

    @Test
    void getCoffeeByExistingIdTest() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));
        when(coffeeMapper.coffeeToCoffeeDto(coffee)).thenReturn(coffeeDto);

        CoffeeDto result = coffeeService.getCoffeeById(1L);

        assertNotNull(result);
        assertEquals("Latte", result.name());
        verify(coffeeRepository, times(1)).findById(1L);
        verify(coffeeMapper, times(1)).coffeeToCoffeeDto(coffee);
    }

    @Test
    public void getCoffeeByNonExistingId_ThrowsNotFoundExceptionTest() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> coffeeService.getCoffeeById(1L));
        verify(coffeeRepository, times(1)).findById(1L);
        verify(coffeeMapper, never()).coffeeToCoffeeDto(any());
    }

    @Test
    public void addCoffee_ValidCoffeeDtoTest() {
        CoffeeIdDto coffeeIdDto = new CoffeeIdDto(1L);
        when(coffeeMapper.coffeeDtoToCoffee(coffeeDto)).thenReturn(coffee);
        when(coffeeRepository.save(coffee)).thenReturn(coffee);
        when(coffeeMapper.coffeeToCoffeeIdDto(coffee)).thenReturn(coffeeIdDto);

        CoffeeIdDto result = coffeeService.addCoffee(coffeeDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(coffeeMapper, times(1)).coffeeDtoToCoffee(coffeeDto);
        verify(coffeeRepository, times(1)).save(coffee);
        verify(coffeeMapper, times(1)).coffeeToCoffeeIdDto(coffee);
    }

    @Test
    public void updateCoffee_ExistingId_ReturnsCoffeeIdDtoTest() {
        CoffeeDto coffeeDto = new CoffeeDto("New Latte",2,3,4,5.0);
        CoffeeIdDto coffeeIdDto = new CoffeeIdDto(1L);


        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));
        when(coffeeMapper.coffeeToCoffeeIdDto(coffee)).thenReturn(coffeeIdDto);

        CoffeeIdDto result = coffeeService.updateCoffee(1L, coffeeDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(coffeeRepository, times(1)).findById(1L);
        verify(coffeeMapper, times(1)).coffeeToCoffeeIdDto(coffee);
    }

    @Test
    public void updateCoffee_NonExistingId_ThrowsNotFoundExceptionTest() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> coffeeService.updateCoffee(1L, coffeeDto));
        verify(coffeeRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteCoffee_ExistingId_DeletesCoffee() {
        doNothing().when(coffeeRepository).deleteById(1L);

        coffeeService.deleteCoffee(1L);

        verify(coffeeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void buyCoffee_ExistingIdAndEnoughFundsAndIngredients_ReturnsCoffeeDtoTest() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));
        when(storageRepository.findAll()).thenReturn(List.of(storage));
        when(coffeeMapper.coffeeToCoffeeDto(coffee)).thenReturn(coffeeDto);

        CoffeeDto result = coffeeService.buyCoffee(1L, amountDto);

        assertNotNull(result);
        assertEquals("Latte", result.name());
        verify(coffeeRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findAll();
        verify(storageRepository, times(1)).save(any());
        verify(purchaseHistoryRepository, times(1)).save(any());
        verify(coffeeMapper, times(1)).coffeeToCoffeeDto(coffee);
    }

    @Test
    public void buyCoffee_NonExistingId_ThrowsNotFoundExceptionTest() {
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> coffeeService.buyCoffee(1L, amountDto));
        verify(coffeeRepository, times(1)).findById(1L);
        verify(storageRepository, never()).findAll();
        verify(storageRepository, never()).save(any());
        verify(purchaseHistoryRepository, never()).save(any());
        verify(coffeeMapper, never()).coffeeToCoffeeDto(any());
    }

    @Test
    public void buyCoffee_NotEnoughFunds_ThrowsNotEnoughFundsExceptionTest() {
        AmountDto insufficientAmountDto = new AmountDto(1.0);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        assertThrows(NotEnoughFundsException.class, () -> coffeeService.buyCoffee(1L, insufficientAmountDto));
        verify(coffeeRepository, times(1)).findById(1L);
        verify(storageRepository, never()).findAll();
        verify(storageRepository, never()).save(any());
        verify(purchaseHistoryRepository, never()).save(any());
        verify(coffeeMapper, never()).coffeeToCoffeeDto(any());
    }

    @Test
    public void buyCoffee_NotEnoughIngredients_ThrowsNotEnoughIngredientsException() {
        Storage insufficientStorage = new Storage()
                .setId(UUID.randomUUID())
                .setMilk(100)
                .setBoiledWater(50)
                .setCoffeeBeans(10);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));
        when(storageRepository.findAll()).thenReturn(List.of(insufficientStorage));

        assertThrows(NotEnoughIngredientsException.class, () -> coffeeService.buyCoffee(1L, amountDto));
        verify(coffeeRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findAll();
        verify(storageRepository, never()).save(any());
        verify(purchaseHistoryRepository, never()).save(any());
        verify(coffeeMapper, never()).coffeeToCoffeeDto(any());
    }

}
