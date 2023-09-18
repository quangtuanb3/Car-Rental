package com.example.case_study_car.service.customer;

import com.example.case_study_car.domain.Customer;
import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }
    public Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

}
