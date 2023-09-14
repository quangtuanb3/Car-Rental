package com.example.case_study_car.controller.rest;


import com.example.case_study_car.repository.AgencyRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/specifications")
@AllArgsConstructor
public class SpecificationRestController {

    private final SpecificationRepository specificationRepository;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption(){
        return specificationRepository.findAll().stream().map(specification -> new SelectOptionResponse(specification.getId().toString(), specification.getName())).collect(Collectors.toList());
    }
}
