package com.example.case_study_car.security.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String numberPhone;

    private String email;

    private String password;

}
