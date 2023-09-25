package com.example.case_study_car.service.bill;

import com.example.case_study_car.domain.*;
import com.example.case_study_car.domain.enumaration.EBillStatus;
import com.example.case_study_car.repository.BillRepository;
import com.example.case_study_car.repository.CarRepository;
import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.service.bill.request.BillSaveRequest;
import com.example.case_study_car.service.bill.response.BillDetailResponse;
import com.example.case_study_car.service.bill.response.BillListResponse;
import com.example.case_study_car.service.bill.response.BillShowDetailResponse;
import com.example.case_study_car.service.customer.CustomerService;
import com.example.case_study_car.service.response.SelectOptionResponse;
import com.example.case_study_car.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
        bill.setBillStatus(EBillStatus.WAITING);
        bill.setCustomer(customer);
        bill.setCleaningFee(car.getCleaningFee());
        bill.setExcessDistanceFee(car.getExcessDistanceFee());
        bill.setOvertimeFee(car.getOvertimeFee());
        bill.setCar(car);
        billRepository.save(bill);

    }

    public BillDetailResponse findById(Long id) {
        Bill bill = billRepository.findById(id).orElse(new Bill());
        return AppUtil.mapper.map(bill, BillDetailResponse.class);
    }

    public BillShowDetailResponse findCarDetailById(Long id) {
       Bill bill = billRepository.findById(id).orElse(new Bill());
            return AppUtil.mapper.map(bill, BillShowDetailResponse.class);
    }

    public Page<BillListResponse> getPageBills(Pageable pageable) {
        return billRepository.pageEverything(pageable).map(e -> {
            var result = new BillListResponse();
            result.setCustomerName(e.getCustomerName());
            result.setId(e.getId());
            result.setCustomerPhoneNumber(e.getCustomerPhoneNumber());
            result.setBillStatus(e.getBillStatus().toString());
            result.setCustomerEmail(e.getCustomerEmail());
            result.setCustomerIdNumber(e.getCustomerIdNumber());
            result.setCarId(e.getCar().getId());
            result.setPickupTime(e.getPickupTime());
            result.setExpectedDropOffTime(e.getExpectedDropOffTime());
            result.setPickupLocation(e.getPickupLocation());
            result.setDropOffLocation(e.getDropOffLocation());
            result.setRentPrice(e.getRentPrice());
            result.setDeliveryFee(e.getDeliveryFee());
            result.setTotalPrice(e.getTotalPrice());
            result.setDeposit(e.getDeposit());
            result.setTradeCode(e.getTradeCode());
            result.setLicensePlate(e.getLicensePlate());
            return result;
        });
    }

    public List<BillListResponse> getAllBills() {
        List<Bill> bills = billRepository.findAll();

        List<BillListResponse> billResponses = bills.stream()
                .map(bill -> {
                    BillListResponse billResponse = new BillListResponse();
                    billResponse.setId(bill.getId());
                    billResponse.setCustomerName(bill.getCustomerName());
                    billResponse.setCustomerPhoneNumber(bill.getCustomerPhoneNumber());
                    billResponse.setCustomerEmail(bill.getCustomerEmail());
                    billResponse.setCustomerIdNumber(bill.getCustomerIdNumber());
                    billResponse.setCarId(bill.getCar().getId());
                    billResponse.setPickupTime(bill.getPickupTime());
                    billResponse.setExpectedDropOffTime(bill.getExpectedDropOffTime());
                    billResponse.setPickupLocation(bill.getPickupLocation());
                    billResponse.setDropOffLocation(bill.getDropOffLocation());
                    billResponse.setRentPrice(bill.getRentPrice());
                    billResponse.setDeliveryFee(bill.getDeliveryFee());
                    billResponse.setTotalPrice(bill.getTotalPrice());
                    billResponse.setDeposit(bill.getDeposit());
                    billResponse.setTradeCode(bill.getTradeCode());
                    billResponse.setLicensePlate(bill.getLicensePlate());
                    billResponse.setBillStatus(bill.getBillStatus().toString());
                    return billResponse;
                })
                .collect(Collectors.toList());

        return billResponses;

    }

    public void changeStatus(Long id, EBillStatus status) {
        Bill bill = getBillById(id);
        if (bill != null) {
            bill.setBillStatus(status);
            billRepository.save(bill);
        } else {
            throw new RuntimeException("Bill not found with id: " + id);
        }
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }
}
