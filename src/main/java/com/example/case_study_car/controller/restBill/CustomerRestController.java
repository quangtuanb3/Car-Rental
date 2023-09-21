package com.example.case_study_car.controller.restBill;


import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/customers")
@AllArgsConstructor
public class CustomerRestController {

    private final CustomerRepository customerRepository;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption(){
        return customerRepository.findAll().stream().map(customer -> new SelectOptionResponse(customer.getId().toString(), customer.getName())).collect(Collectors.toList());
    }
}
