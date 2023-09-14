package com.example.case_study_car.controller.rest;


import com.example.case_study_car.repository.FeatureRepository;
import com.example.case_study_car.repository.SurchargeRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/features")
@AllArgsConstructor
public class FeatureRestController {

    private final FeatureRepository featureRepository;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption(){
        return featureRepository.findAll().stream().map(feature -> new SelectOptionResponse(feature.getId().toString(), feature.getName())).collect(Collectors.toList());
    }
}
