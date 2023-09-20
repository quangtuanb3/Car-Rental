package com.example.case_study_car.domain;

import com.example.case_study_car.domain.enumaration.EAccountStatus;
import com.example.case_study_car.domain.enumaration.ERole;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private EAccountStatus status;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToOne(mappedBy = "user")
    private Customer customer;

}
