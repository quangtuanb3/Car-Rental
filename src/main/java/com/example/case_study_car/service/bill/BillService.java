package com.example.case_study_car.service.bill;

import com.example.case_study_car.domain.*;
import com.example.case_study_car.repository.BillRepository;
import com.example.case_study_car.repository.CarRepository;
import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.service.bill.request.BillSaveRequest;
import com.example.case_study_car.service.customer.CustomerService;
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
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;


    public List<SelectOptionResponse> findAll() {
        return billRepository.findAll().stream()
                .map(bill -> new SelectOptionResponse(bill.getId().toString(), bill.getCustomerName()))
                .collect(Collectors.toList());
    }

    public void create(BillSaveRequest request) {
        Bill bill = AppUtil.mapper.map(request, Bill.class);
        Car car = carRepository.findById(Long.valueOf(request.getCarId())).orElse(new Car());
        Customer customer = customerService.findByEmail(request.getCustomerEmail());
        bill.setCustomer(customer);
        bill.setCleaningFee(car.getCleaningFee());
        bill.setExcessDistanceFee(car.getExcessDistanceFee());
        bill.setOvertimeFee(car.getOvertimeFee());
        bill.setCar(car);
       var save =  billRepository.save(bill);
        System.out.println(save);
    }
}
