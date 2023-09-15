package com.example.case_study_car.controller;

import com.example.case_study_car.service.car.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class UserController {
    private final CarService carService;
    @GetMapping("/car")
    public ModelAndView car() {
        ModelAndView view = new ModelAndView("user/car");
        return view;
    }
    @GetMapping("")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("user/index");
        return view;
    }

    @GetMapping("car-detail/{id}")
    public ModelAndView detail(@PathVariable String id) {
        ModelAndView view = new ModelAndView("user/car-detail");
        view.addObject("car", carService.getCarDetailById(Long.valueOf(id)));
        return view;
    }

    @GetMapping("pricing")
    public ModelAndView pricing() {
        ModelAndView view = new ModelAndView("user/pricing");
        return view;
    }


    @GetMapping("car-detail")
    public ModelAndView detail() {
        ModelAndView view = new ModelAndView("user/car-detail");
        return view;
    }

}
