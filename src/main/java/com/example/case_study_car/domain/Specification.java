package com.example.case_study_car.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "specifications")
@Data
@NoArgsConstructor
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String svg;

    @OneToMany(mappedBy = "specification")
    private List<CarSpecification> carSpecifications;

    public Specification(Long id) {
        this.id = id;
    }

    public Specification(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
