package com.example.case_study_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CarNotFoundException extends RuntimeException{
    //hello kitty

    public CarNotFoundException(String message) {
        super(message);
    }
}