package com.example.case_study_car.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Image(Long id) {
        this.id = id;
    }

    public Image(String url, Car car) {
        this.url = url;
        this.car = car;
    }
    //    public Image(Car car, Customer customer) {
//        this.car = car;
//        this.customer= customer;
//    }
}
