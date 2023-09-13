package com.example.case_study_car.service.specification;

import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpecificationService {

    private final SpecificationRepository specificationRepository;


    public List<SelectOptionResponse> findAll(){
        return specificationRepository.findAll().stream()
                .map(specification -> new SelectOptionResponse(specification.getId().toString(), specification.getName()))
                .collect(Collectors.toList());
    }
}
