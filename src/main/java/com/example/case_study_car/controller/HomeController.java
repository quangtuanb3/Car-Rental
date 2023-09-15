package com.example.case_study_car.controller;


import com.example.case_study_car.service.agency.AgencyService;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.feature.FeatureService;
import com.example.case_study_car.service.specification.SpecificationService;
import com.example.case_study_car.service.surcharge.SurchargeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    private final SpecificationService specificationService;
    private final AgencyService agencyService;
    private final SurchargeService surchargeService;
    private final FeatureService featureService;


    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("admin/index");
        view.addObject("specifications", specificationService.getSpecifications());
        view.addObject("agencies", agencyService.findAll());
        view.addObject("surcharges", surchargeService.findAll());
        view.addObject("features", featureService.findAll());
        return view;
    }

}
