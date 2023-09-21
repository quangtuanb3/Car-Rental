package com.example.case_study_car.controller;


import com.example.case_study_car.domain.Car;
import com.example.case_study_car.domain.UserLogin;
import com.example.case_study_car.service.car.response.UserCarDetailResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.case_study_car.service.car.CarService;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class UserController {
    private final CarService carService;

    @GetMapping("/cars")
    public ModelAndView car() {
        ModelAndView view = new ModelAndView("user/car");
        return view;
    }

//    @GetMapping
//    public ModelAndView index() {
//        ModelAndView view = new ModelAndView("user/index");
//
//        return view;
//    }

    @GetMapping()
    public ModelAndView Home(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("user/index");
        view.addObject("message", message);

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

    @GetMapping("/cars/available-cars")
    public ModelAndView getCars(
            @PageableDefault(size = 4) Pageable pageable,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") LocalDateTime pickup,
            @RequestParam(defaultValue = "") LocalDateTime dropOff
    ) {
        return new ModelAndView("user/car");

    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLogin userLogin) {
        System.out.println(userLogin.toString());
        return "user/index";
    }

}
