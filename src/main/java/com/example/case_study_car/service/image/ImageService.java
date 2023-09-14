package com.example.case_study_car.service.image;

import com.example.case_study_car.repository.ImageRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;


    public List<SelectOptionResponse> findAll(){
        return imageRepository.findAll().stream()
                .map(image -> new SelectOptionResponse(image.getId().toString(), image.getUrl()))
                .collect(Collectors.toList());
    }
}
