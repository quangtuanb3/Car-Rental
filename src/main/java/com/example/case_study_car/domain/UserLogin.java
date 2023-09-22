package com.example.case_study_car.domain;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserLogin {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "UserLogin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
