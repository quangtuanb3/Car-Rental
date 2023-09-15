package com.example.case_study_car.service.car.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BestCarResponse {

    private Long id;

    private String name;

    private BigDecimal priceDays;

    private String description;

    private String agency;

    private List<String> urlImages;

}
