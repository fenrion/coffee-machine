package com.aisa.test.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель отображения статистики по конкретному кофе")
public record StatisticsByCoffeeDto(
        @Schema(description = "Название кофе")
        String name,
        @Schema(description = "Количество покупок")
        Integer numberOfPurchase,
        @Schema(description = "Выручка")
        Double money
) {
}
