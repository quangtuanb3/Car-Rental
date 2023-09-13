package com.example.case_study_car.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerPhoneNumber;

    private String customerEmail;

    private String customerIdNumber;

    private LocalDateTime dateReceived;

    private String pickupLocation;
    
    private String dropOffLocation;

    private LocalDateTime expectedDateReturn;

    private LocalDateTime actualDateReturn;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "bill")
    private List<BillCar> billCars;

    @OneToMany(mappedBy = "bill")
    private List<BillCarSurcharge> billCarSurcharges;


}
