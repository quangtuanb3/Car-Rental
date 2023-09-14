package com.example.case_study_car.service.bill;

import com.example.case_study_car.repository.BillRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BillService {

    private final BillRepository billRepository;


    public List<SelectOptionResponse> findAll(){
        return billRepository.findAll().stream()
                .map(bill -> new SelectOptionResponse(bill.getId().toString(), bill.getCustomerName()))
                .collect(Collectors.toList());
    }
}
