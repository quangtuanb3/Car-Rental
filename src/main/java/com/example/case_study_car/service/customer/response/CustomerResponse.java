package com.example.case_study_car.service.customer.response;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomerResponse {
    private Long id;

    private String name;

    private String numberPhone;

    private String email;

    private String idNumber;
}
