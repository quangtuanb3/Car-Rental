package com.example.case_study_car.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "surcharges")
@Entity
@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
public class Surcharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "billSurcharge_id")
    private BillSurcharge billSurcharge;




}
