package com.example.case_study_car.service.customer;

import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;


    public List<SelectOptionResponse> findAll(){
        return customerRepository.findAll().stream()
                .map(customer -> new SelectOptionResponse(customer.getId().toString(), customer.getName()))
                .collect(Collectors.toList());
    }
}
