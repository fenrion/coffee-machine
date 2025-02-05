package com.aisa.test.service;

import com.aisa.test.domain.dto.*;

import java.util.List;

public interface CoffeeService {
    List<CoffeeAllDto> getCoffeeList();
    CoffeeDto getCoffeeById(Long coffeeID);
    CoffeeIdDto addCoffee(CoffeeDto coffeeDto);
    CoffeeIdDto updateCoffee(Long coffeeID, CoffeeDto coffeeDto);
    void deleteCoffee(Long coffeeID);
    CoffeeDto buyCoffee(Long coffeeID, AmountDto amountDto);

}
