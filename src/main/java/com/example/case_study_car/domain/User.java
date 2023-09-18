package com.example.case_study_car.domain;

import com.example.case_study_car.domain.enumaration.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private ERole role;

    @OneToOne(mappedBy = "user")
    private Customer customer;

}
