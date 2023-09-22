package com.example.case_study_car.security.auth;

import com.example.case_study_car.domain.Customer;
import com.example.case_study_car.domain.User;
import com.example.case_study_car.domain.enumaration.EAccountStatus;
import com.example.case_study_car.domain.enumaration.ERole;
import com.example.case_study_car.repository.CustomerRepository;
import com.example.case_study_car.repository.UserRepository;
import com.example.case_study_car.security.auth.request.RegisterRequest;
import com.example.case_study_car.util.AppUtil;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;

    public void register(RegisterRequest request) {
        var customer = AppUtil.mapper.map(request, Customer.class);
        customerRepository.save(customer);
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(ERole.ROLE_USER)
                .customer(customer)
                .password(passwordEncoder.encode(request.getPassword()))
                .status(EAccountStatus.UNVERIFIED)
                .build();
        userRepository.save(user);
    }

    public boolean checkEmail(RegisterRequest request, BindingResult result) {
        boolean check = false;
        if (customerRepository.existsByEmailIgnoreCase(request.getEmail())) {
            result.rejectValue("email", "email",
                    "Another account is already registered with this email");
            check = true;
        }
        if (customerRepository.existsByNumberPhone(request.getNumberPhone())) {
            result.rejectValue("numberPhone", "numberPhone",
                    "Another account is already registered with this number");
            check = true;
        }
        return check;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Exist"));
        var role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(user.getRole().toString()));
//        boolean isVerified = EAccountStatus.VERIFIED.equals(user.getStatus());
//        boolean isLocked = EAccountStatus.BLOCK.equals(user.getStatus());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), role);
    }




    // để làm 1. kiểm tra xem user có tồn tại trong hệ thông hay không và tìm bằng 3 field Username Email PhoneNumber
    // 2. Nếu có thì sẽ trả về User của .security.core.userdetails.User để nó lưu vô kho spring sercurity context holder
    // 3. nếu không thì trả ra message lỗi User not Exist
}