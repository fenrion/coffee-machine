package com.aisa.test.domain.mapper;

import com.aisa.test.domain.dto.CoffeeAllDto;
import com.aisa.test.domain.dto.CoffeeDto;
import com.aisa.test.domain.dto.CoffeeIdDto;
import com.aisa.test.domain.model.Coffee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoffeeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    CoffeeAllDto coffeeToCoffeeAllDto(Coffee coffee);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "milk", source = "milk")
    @Mapping(target = "boiledWater", source = "boiledWater")
    @Mapping(target = "coffeeBeans", source = "coffeeBeans")
    @Mapping(target = "price", source = "price")
    Coffee coffeeDtoToCoffee(CoffeeDto coffeeDto);

    @Mapping(target = "id", source = "id")
    CoffeeIdDto coffeeToCoffeeIdDto(Coffee coffee);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "milk", source = "milk")
    @Mapping(target = "boiledWater", source = "boiledWater")
    @Mapping(target = "coffeeBeans", source = "coffeeBeans")
    @Mapping(target = "price", source = "price")
    CoffeeDto coffeeToCoffeeDto(Coffee coffee);
}
