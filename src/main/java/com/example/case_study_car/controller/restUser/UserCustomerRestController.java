package com.example.case_study_car.controller.restUser;

import com.example.case_study_car.service.customer.CustomerService;
import com.example.case_study_car.service.customer.response.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/api/customers")
@AllArgsConstructor
public class UserCustomerRestController {
    private final CustomerService customerService;
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCurrentCar(@PathVariable String id) {
        return new ResponseEntity<>(customerService.getCustomerResponseByID(Long.valueOf(id)), HttpStatus.OK);

    }
}
