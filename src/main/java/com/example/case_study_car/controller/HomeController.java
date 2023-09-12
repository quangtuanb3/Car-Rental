package com.example.case_study_car.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {


    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
//        view.addObject("categories", categoryService.findAll());
//        view.addObject("types", typeService.findAll());
        return view;
    }

}
