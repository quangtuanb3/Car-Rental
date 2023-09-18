package com.example.case_study_car.service.bill;

import com.example.case_study_car.domain.*;
import com.example.case_study_car.repository.BillRepository;
import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.repository.SurchargeRepository;
import com.example.case_study_car.service.bill.request.BillSaveRequest;
import com.example.case_study_car.service.response.SelectOptionResponse;
import com.example.case_study_car.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BillService {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final SurchargeRepository surchargeRepository;

    public List<SelectOptionResponse> findAll() {
        return billRepository.findAll().stream()
                .map(bill -> new SelectOptionResponse(bill.getId().toString(), bill.getCustomerName()))
                .collect(Collectors.toList());
    }

    public void create(BillSaveRequest request) {
        Bill bill = AppUtil.mapper.map(request, Bill.class);
        Customer customer = customerRepository.findCustomerByEmail(request.getCustomerEmail())
                .orElse(new Customer());
        bill.setCustomer(customer);




        BillCar billCar = BillCar.builder()
                .car(new Car(Long.valueOf(request.getCarId())))

                .build();


        System.out.println(bill);
    }
}
