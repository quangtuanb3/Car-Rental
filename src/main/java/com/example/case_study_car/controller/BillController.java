package com.example.case_study_car.controller;

import com.example.case_study_car.domain.enumaration.EBillStatus;
import com.example.case_study_car.domain.enumaration.ECarStatus;
import com.example.case_study_car.service.agency.AgencyService;
import com.example.case_study_car.service.bill.BillService;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.apache.catalina.webresources.CachedResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bill")
@AllArgsConstructor
public class BillController {

    private final CustomerService customerService;

    private final CarService carService;

    private final BillService billService;

    @GetMapping
    public ModelAndView bill() {
        ModelAndView view = new ModelAndView("admin/bill");
        view.addObject("customers", customerService.findAll());
        view.addObject("cars", carService.findAll());
        view.addObject("billStatuses", EBillStatus.values());
        view.addObject("bills", billService.getAllBills());

        return view;
    }
}
