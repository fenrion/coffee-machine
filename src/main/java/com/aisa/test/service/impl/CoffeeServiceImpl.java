package com.aisa.test.service.impl;

import com.aisa.test.domain.dto.*;
import com.aisa.test.domain.exception.CoffeeNotFoundException;
import com.aisa.test.domain.exception.NotEnoughFundsException;
import com.aisa.test.domain.exception.NotEnoughIngredientsException;
import com.aisa.test.domain.mapper.CoffeeMapper;
import com.aisa.test.domain.model.Coffee;
import com.aisa.test.domain.model.PurchaseHistory;
import com.aisa.test.domain.model.Storage;
import com.aisa.test.repository.CoffeeRepository;
import com.aisa.test.repository.PurchaseHistoryRepository;
import com.aisa.test.repository.StorageRepository;
import com.aisa.test.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final StorageRepository storageRepository;
    private final CoffeeMapper coffeeMapper;

    @Override
    @Transactional
    public List<CoffeeAllDto> getCoffeeList() {
        log.info("Поиск всех видов кофе");
        return coffeeRepository.findAll()
                .stream()
                .map(coffeeMapper::coffeeToCoffeeAllDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CoffeeDto getCoffeeById(Long coffeeID) {
        log.info("Поиск кофе c id {}", coffeeID);
        Coffee coffee = coffeeRepository.findById(coffeeID)
                .orElseThrow(() -> new CoffeeNotFoundException("Кофе с таким id не найден"));
        return coffeeMapper.coffeeToCoffeeDto(coffee);
    }

    @Override
    @Transactional
    public CoffeeIdDto addCoffee(CoffeeDto coffeeDto) {
        log.info("Добавление рецепта кофе в базу");
        Coffee coffee = coffeeMapper.coffeeDtoToCoffee(coffeeDto);
        Coffee savedCoffee = coffeeRepository.save(coffee);
        return coffeeMapper.coffeeToCoffeeIdDto(savedCoffee);
    }

    @Override
    @Transactional
    public CoffeeIdDto updateCoffee(Long coffeeID, CoffeeDto coffeeDto) {
        log.info("Поиск кофе по id {} для обновления рецепта", coffeeID);
        Coffee coffee = coffeeRepository.findById(coffeeID)
                .orElseThrow(() -> new CoffeeNotFoundException("Кофе с таким id не найден"));
        log.info("Обновление рецепта");
        BeanUtils.copyProperties(coffeeDto, coffee);
        return coffeeMapper.coffeeToCoffeeIdDto(coffee);
    }

    @Override
    @Transactional
    public void deleteCoffee(Long coffeeID) {
        log.info("Удаление рецепта кофе с id {}", coffeeID);
        coffeeRepository.deleteById(coffeeID);
    }

    @Override
    @Transactional
    public CoffeeDto buyCoffee(Long coffeeID, AmountDto amountDto) {
        log.info("Попытка покупки кофе с id {} за {} денег", coffeeID, amountDto.amount());

        log.info("Поиск кофе c id {}", coffeeID);
        Coffee coffee = coffeeRepository.findById(coffeeID)
                .orElseThrow(() -> new CoffeeNotFoundException("Кофе с таким id не найден"));

        if (coffee.getPrice()>amountDto.amount()) {
            log.info("Недостаточно средств для покупки");
            throw new NotEnoughFundsException("Недостаточно средств");
        }

        log.info("Проверка наличия ингредиентов");
        Storage storage = storageRepository.findAll().get(0);
        if (!hasEnoughIngredients(storage, coffee)) {
            log.info("Недостаточно ингредиентов");
            throw new NotEnoughIngredientsException("Недостаточно ингредиентов");
        }

        log.info("Уменьшение запасов ингредиентов и сохранение в БД");
        storage.setMilk(storage.getMilk()-coffee.getMilk());
        storage.setBoiledWater(storage.getBoiledWater()-coffee.getBoiledWater());
        storage.setCoffeeBeans(storage.getCoffeeBeans()-coffee.getCoffeeBeans());
        storageRepository.save(storage);

        log.info("Сохранение операции");
        LocalDate dateOfOperation = LocalDate.now();
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setCoffee(coffee);
        purchaseHistory.setOperationDate(dateOfOperation);
        purchaseHistoryRepository.save(purchaseHistory);

        return coffeeMapper.coffeeToCoffeeDto(coffee);
    }

    private boolean hasEnoughIngredients(Storage storage, Coffee coffee) {
        if (storage == null || coffee == null){
            return false;
        }
        return Stream.of(
                storage.getMilk() >= coffee.getMilk(),
                storage.getBoiledWater() >= coffee.getBoiledWater(),
                storage.getCoffeeBeans() >= coffee.getCoffeeBeans()
        ).allMatch(Boolean::booleanValue);
    }
}
