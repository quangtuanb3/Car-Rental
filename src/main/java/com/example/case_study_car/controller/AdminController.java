package com.example.case_study_car.controller;

import com.example.case_study_car.domain.enumaration.ECarStatus;
import com.example.case_study_car.domain.enumaration.ESpecificationType;
import com.example.case_study_car.service.agency.AgencyService;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.feature.FeatureService;
import com.example.case_study_car.service.specification.SpecificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class AdminController {

    private final SpecificationService specificationService;
    private final AgencyService agencyService;
    private final FeatureService featureService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("admin/index");
        view.addObject("seats", specificationService.findSpecificallySpecification(ESpecificationType.SEAT));
        view.addObject("fuel", specificationService.findSpecificallySpecification(ESpecificationType.FUEL));
        view.addObject("luggage", specificationService.findSpecificallySpecification(ESpecificationType.LUGGAGE));
        view.addObject("transmission", specificationService.findSpecificallySpecification(ESpecificationType.TRANSMISSION));
        view.addObject("specifications", specificationService.findAll());
        view.addObject("agencies", agencyService.findAll());
        view.addObject("status", ECarStatus.values());
        view.addObject("features", featureService.findAll());
        return view;
    }
}
