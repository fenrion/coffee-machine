package com.aisa.test.controller.impl;

import com.aisa.test.controller.CoffeeController;
import com.aisa.test.domain.dto.*;
import com.aisa.test.service.CoffeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CoffeeControllerImpl implements CoffeeController {

    private final CoffeeService coffeeService;


    @Override
    public List<CoffeeAllDto> getCoffeeList() {
        return coffeeService.getCoffeeList();
    }

    @Override
    public CoffeeDto getCoffeeById(Long coffeeID) {
        return coffeeService.getCoffeeById(coffeeID);
    }

    @Override
    public CoffeeIdDto addCoffee(CoffeeDto coffeeDto) {
        return coffeeService.addCoffee(coffeeDto);
    }

    @Override
    public CoffeeIdDto updateCoffee(Long coffeeID, CoffeeDto coffeeDto) {
        return coffeeService.updateCoffee(coffeeID, coffeeDto);
    }

    @Override
    public void deleteCoffee(Long coffeeID) {
        coffeeService.deleteCoffee(coffeeID);
    }

    @Override
    public CoffeeDto buyCoffee(Long coffeeID, AmountDto amountDto) {
        return coffeeService.buyCoffee(coffeeID, amountDto);
    }
}
