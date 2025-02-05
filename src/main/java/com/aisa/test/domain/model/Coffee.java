package com.aisa.test.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "COFFEE", schema = "COFFEE_MACHINE")
@Data
@Accessors(chain = true)
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    @Size(min = 2, max = 100, message = "Название кофе должно содержать от 2 до 100 символов.")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Некорректное название кофе. Используйте только буквы и пробелы")
    private String name;

    @Column(name = "MILK", nullable = false)
    private Integer milk;

    @Column(name = "BOILED_WATER", nullable = false)
    private Integer boiledWater;

    @Column(name = "COFFEE_BEANS", nullable = false)
    private Integer coffeeBeans;

    @Column(name = "PRICE", nullable = false)
    private Double price;
}
