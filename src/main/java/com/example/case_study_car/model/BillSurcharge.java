package com.example.case_study_car.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "bill_surcharges")
@Entity
@Data
@NoArgsConstructor
public class BillSurcharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "surcharge_id")
    private Surcharge surcharge;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
