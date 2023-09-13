package com.example.case_study_car.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "customers")
@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String numberPhone;

    private String email;
    private String idNumber;

    @OneToMany(mappedBy = "customer")
    private List<Image> identification;

    @OneToMany(mappedBy = "customer")
    private List<Bill> bills;
}
