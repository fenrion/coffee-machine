package com.aisa.test.controller;

import com.aisa.test.advice.GlobalExceptionHandler;
import com.aisa.test.domain.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("coffee-machine")
@Validated
public interface CoffeeController {

    @Operation(summary = "Просмотр списка кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CoffeeAllDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @GetMapping("/coffee-list")
    List<CoffeeAllDto> getCoffeeList();

    @Operation(summary = "Просмотр кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CoffeeDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @GetMapping("/coffee-list/{coffeeID}")
    CoffeeDto getCoffeeById(@PathVariable Long coffeeID);

    @Operation(summary = "Добавление нового кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CoffeeIdDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CoffeeIdDto addCoffee(@Valid @RequestBody CoffeeDto coffeeDto);

    @Operation(summary = "Изменение кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CoffeeIdDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/coffee-list/{coffeeID}")
    CoffeeIdDto updateCoffee(@PathVariable Long coffeeID, @Valid @RequestBody CoffeeDto coffeeDto);

    @Operation(summary = "Удаление кофе")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/coffee-list/{coffeeID}")
    void deleteCoffee(@PathVariable Long coffeeID);

    @Operation(summary = "Покупка кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CoffeeDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/coffee-list/{coffeeID}")
    CoffeeDto buyCoffee(@PathVariable Long coffeeID, @RequestBody AmountDto amountDto);
}
