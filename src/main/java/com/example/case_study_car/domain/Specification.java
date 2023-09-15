package com.example.case_study_car.domain;

import com.example.case_study_car.domain.enumaration.ESpecificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "specifications")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ESpecificationType type;

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

    public Specification(String name, ESpecificationType type) {
        this.name = name;
        this.type = type;
    }
}
