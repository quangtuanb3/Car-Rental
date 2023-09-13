package com.example.case_study_car.controller;


import com.example.case_study_car.service.agency.AgencyService;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.specification.SpecificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final SpecificationService specificationService;
    private final AgencyService agencyService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("user/index");
        return view;
    }
    @GetMapping("/car")
    public ModelAndView car() {
        ModelAndView view = new ModelAndView("user/car");
        return view;
    }
    @GetMapping("car-detail")
    public ModelAndView detail() {
        ModelAndView view = new ModelAndView("user/car-detail");
        return view;
    }
    @GetMapping("pricing")
    public ModelAndView pricinge () {
        ModelAndView view = new ModelAndView("user/pricing");
        return view;
    }

}
