package com.example.case_study_car.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "customers")
@Entity
@Getter
@Setter
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Customer(Long id) {
        this.id = id;
    }
}
