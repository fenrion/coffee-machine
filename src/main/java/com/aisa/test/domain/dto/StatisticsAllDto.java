package com.aisa.test.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Модель для отображения статистики")
public record StatisticsAllDto(
        @Schema(description = "Название и количество покупок")
        Map<String, Long> stats
) {
}
