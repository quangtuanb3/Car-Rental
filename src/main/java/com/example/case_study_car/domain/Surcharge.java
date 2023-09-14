package com.example.case_study_car.domain;

import jakarta.persistence.*;
import lombok.*;

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


}
