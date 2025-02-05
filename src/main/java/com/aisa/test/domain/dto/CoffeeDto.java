package com.aisa.test.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Модель данных информации о кофе")
public record CoffeeDto(

        @Schema(description = "Название кофе")
        @NotBlank(message = "Название кофе не может быть пустым")
        @Size(min = 2, max = 100, message = "Название кофе должно содержать от 2 до 100 символов")
        String name,

        @Schema(description = "Количество молока в мл.")
        @NotNull(message = "Количество молока не может быть null")
        @Min(value = 0, message = "Количество молока не может быть отрицательным")
        @Max(value = 500, message = "Количество молока не может превышать 500 мл")
        Integer milk,

        @Schema(description = "Количество кипятка в мл.")
        @NotNull(message = "Количество кипятка не может быть null")
        @Min(value = 0, message = "Количество кипятка не может быть отрицательным")
        @Max(value = 500, message = "Количество кипятка не может превышать 500 мл")
        Integer boiledWater,

        @Schema(description = "Количество кофейных зерен в граммах")
        @NotNull(message = "Количество кофейных зерен не может быть null")
        @Min(value = 5, message = "Минимальное количество кофейных зерен - 5 грамм")
        @Max(value = 50, message = "Максимальное количество кофейных зерен - 50 грамм")
        Integer coffeeBeans,

        @Schema(description = "Цена")
        @NotNull(message = "Цена не может быть null")
        @DecimalMin(value = "0.01", message = "Цена должна быть больше 0") // Используем DecimalMin для Double
        @DecimalMax(value = "500.00", message = "Цена не может превышать 500")
        Double price
) {
}
