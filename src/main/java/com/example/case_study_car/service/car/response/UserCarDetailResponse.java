package com.example.case_study_car.service.car.response;

import com.example.case_study_car.domain.Specification;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class UserCarDetailResponse {

    private Long id;

    private String name;

    private String status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    private String description;

    private String agency;

    private List<Specification> specifications;

    private List<String> features;

    private List<String> urlImages;

}
