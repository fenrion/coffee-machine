package com.aisa.test.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель данных для просмотра списка кофе")
public record CoffeeAllDto(
        @Schema(description = "id кофе")
        Long id,
        @Schema(description = "Название кофе")
        String name,
        @Schema(description = "Цена")
        Double price
) {
}
