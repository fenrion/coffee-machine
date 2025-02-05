package com.aisa.test.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Модель для оплаты заказа")
public record AmountDto(
        @Schema(description = "Сумма денег")
        @Min(value = 0, message = "Сумма должна быть положительной")
        Double amount
) {
}
