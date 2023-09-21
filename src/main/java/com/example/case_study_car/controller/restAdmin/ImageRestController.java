package com.example.case_study_car.controller.restAdmin;
import com.example.case_study_car.domain.Image;
import com.example.case_study_car.repository.ImageRepository;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.image.ImageResponse;
import com.example.case_study_car.service.image.ImageService;
import com.example.case_study_car.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/image")
@AllArgsConstructor
public class ImageRestController {

    private final ImageService imageService;

    @PostMapping
    public Image upload(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        return imageService.saveAvatar(avatar);
    }

}
