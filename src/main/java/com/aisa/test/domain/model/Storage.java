package com.aisa.test.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Table(name = "STORAGE", schema = "COFFEE_MACHINE")
@Data
@Accessors(chain = true)
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "MILK")
    @Min(value = 0, message = "Не хватает молока")
    private Integer milk;

    @Column(name = "BOILED_WATER")
    @Min(value = 0, message = "Не хватает кипятка")
    private Integer boiledWater;

    @Column(name = "COFFEE_BEANS")
    @Min(value = 0, message = "Не хватает кофейных зерен")
    private Integer coffeeBeans;
}
