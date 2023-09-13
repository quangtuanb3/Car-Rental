package com.example.case_study_car.controller.rest;


import com.example.case_study_car.repository.AgencyRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/agencies")
@AllArgsConstructor
public class AgencyRestController {

    private final AgencyRepository agencyRepository;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption(){
        return agencyRepository.findAll().stream().map(agency -> new SelectOptionResponse(agency.getId().toString(), agency.getName())).collect(Collectors.toList());
    }
}
