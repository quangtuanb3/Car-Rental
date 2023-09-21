package com.example.case_study_car.service.car.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarPaginationResponse {

    private Long id;

    private String name;

    private BigDecimal priceDays;

    private String description;

    private String urlImages;
}
