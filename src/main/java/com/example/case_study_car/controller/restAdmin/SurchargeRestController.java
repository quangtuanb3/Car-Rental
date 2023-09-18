//package com.example.case_study_car.controller.restAdmin;
//
//import com.example.case_study_car.repository.SurchargeRepository;
//import com.example.case_study_car.service.response.SelectOptionResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("api/surcharges")
//@AllArgsConstructor
//public class SurchargeRestController {
//
//    private final SurchargeRepository surchargeRepository;
//
//    @GetMapping
//    public List<SelectOptionResponse> getSelectOption(){
//        return surchargeRepository.findAll().stream().map(surcharge -> new SelectOptionResponse(surcharge.getId().toString(), surcharge.getName())).collect(Collectors.toList());
//    }
//}
