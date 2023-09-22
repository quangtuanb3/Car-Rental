package com.example.case_study_car.security.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurity {

    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .requestMatchers("/socket", "/errors", "/403").permitAll()
                                .requestMatchers("/admin/public").permitAll()
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/cars/**").permitAll()
                                .requestMatchers("user/api/customer-detail/**").permitAll()
                                .requestMatchers("/pricing").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/car-detail/**").permitAll()
                                .requestMatchers("/home").hasAnyRole("ADMIN")
                                .requestMatchers("/bill").hasAnyRole("ADMIN")
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/user/api/cars/rent").hasAnyRole("USER")
                                .requestMatchers("/user/api/customers/**").permitAll()
                                .requestMatchers("/user/api/cars/check-available").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/user/api/cars/related-cars/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(customAuthenticationSuccessHandler())
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                                .logoutSuccessUrl("/?message=Logout%20successfully")
                )
                .exceptionHandling()
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/403");
                        })
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/errors");
                });
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) // config để biết class này dùng để check user login
                .passwordEncoder(passwordEncoder()); // loại mã hóa password
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();

        successHandler.setDefaultTargetUrl("/?message=Login%20successfully"); // Set the default target URL
        successHandler.setAlwaysUseDefaultTargetUrl(false); // Ensure the default target URL is always used
        return successHandler;
    }
}