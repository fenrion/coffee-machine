package com.aisa.test.controller;

import com.aisa.test.advice.GlobalExceptionHandler;
import com.aisa.test.domain.dto.StatisticsAllDto;
import com.aisa.test.domain.dto.StatisticsByCoffeeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("statistics")
@Validated
public interface StatisticsController {
    @Operation(summary = "Просмотр статистики покупок по каждому кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = StatisticsAllDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @GetMapping
    StatisticsAllDto getAllStatistics();

    @Operation(summary = "Просмотр статистики по конкретному виду кофе")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = StatisticsByCoffeeDto.class))})
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))})
    @GetMapping("/{coffeeID}")
    StatisticsByCoffeeDto getStatisticsByCoffee(@PathVariable Long coffeeID);
}
