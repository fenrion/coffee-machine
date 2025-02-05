package com.aisa.test.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "PURCHASE_HISTORY", schema = "COFFEE_MACHINE")
@Data
@Accessors(chain = true)
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "COFFEE_ID", referencedColumnName = "ID")
    private Coffee coffee;

    @Column(name = "OPERATION_DATE")
    private LocalDate operationDate;
}
