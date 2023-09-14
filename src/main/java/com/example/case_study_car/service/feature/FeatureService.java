package com.example.case_study_car.service.feature;

import com.example.case_study_car.repository.FeatureRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FeatureService {

    private final FeatureRepository featureRepository;


    public List<SelectOptionResponse> findAll(){
        return featureRepository.findAll().stream()
                .map(feature -> new SelectOptionResponse(feature.getId().toString(), feature.getName()))
                .collect(Collectors.toList());
    }
}
