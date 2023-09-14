package com.example.case_study_car.service.surcharge;

import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.repository.SurchargeRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SurchargeService {

    private final SurchargeRepository surchargeRepository;


    public List<SelectOptionResponse> findAll(){
        return surchargeRepository.findAll().stream()
                .map(surcharge -> new SelectOptionResponse(surcharge.getId().toString(), surcharge.getName()))
                .collect(Collectors.toList());
    }

}
