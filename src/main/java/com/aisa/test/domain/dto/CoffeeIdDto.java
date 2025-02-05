package com.aisa.test.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель для возвращения Id кофе")
public record CoffeeIdDto(

        @Schema(description = "id")
        Long id
) {
}
