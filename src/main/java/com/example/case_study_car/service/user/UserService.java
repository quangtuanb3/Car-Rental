package com.example.case_study_car.service.user;

import com.example.case_study_car.domain.User;
import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(()-> new RuntimeException("Not found!"));
    }
}
