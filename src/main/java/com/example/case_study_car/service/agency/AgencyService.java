package com.example.case_study_car.service.agency;

import com.example.case_study_car.repository.AgencyRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgencyService {

    private AgencyRepository agencyRepository;
    public List<SelectOptionResponse> findAll(){
        return agencyRepository.findAll().stream()
                .map(agency -> new SelectOptionResponse(agency.getId().toString(), agency.getName()))
                .collect(Collectors.toList());
    }
}
