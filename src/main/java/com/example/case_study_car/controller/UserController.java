
package com.example.case_study_car.controller;


        import lombok.AllArgsConstructor;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class UserController {


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