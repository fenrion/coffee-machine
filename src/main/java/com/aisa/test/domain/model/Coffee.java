package com.aisa.test.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;


import java.util.UUID;

@Entity
@Table(name = "COFFEE", schema = "COFFEE_MACHINE")
@Data
@Accessors(chain = true)
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    @Size(min = 1, max = 100, message = "Название кофе должно содержать от 1 до 100 символов.")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Некорректное название кофе. Используйте только буквы, цифры, пробелы и дефисы.")
    private String name;

    @Column(name = "MILK")
    private Integer milk;

    @Column(name = "BOILED_WATER")
    private Integer boiledWater;

    @Column(name = "COFFEE_BEANS")
    private Integer coffeeBeans;

    @Column(name = "PRICE")
    private Double price;
}
