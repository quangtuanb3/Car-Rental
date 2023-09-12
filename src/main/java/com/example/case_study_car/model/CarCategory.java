package com.example.case_study_car.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "car_categories")
@Entity
@Data
@NoArgsConstructor
public class CarCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "car_id")
    private Car car;


    @ManyToOne
//    @JoinColumn(name = "category_id")
    private Category category;

    public CarCategory(Car car, Category category) {
        this.car = car;
        this.category = category;
    }
}
