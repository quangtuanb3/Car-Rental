package com.example.case_study_car.controller.rest;


import com.example.case_study_car.repository.ImageRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/files")
@AllArgsConstructor
public class ImageRestController {

    private final ImageRepository imageRepository;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption(){
        return imageRepository.findAll().stream().map(image -> new SelectOptionResponse(image.getId().toString(), image.getUrl())).collect(Collectors.toList());
    }
}
