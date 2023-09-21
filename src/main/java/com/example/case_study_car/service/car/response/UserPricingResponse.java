package com.example.case_study_car.service.car.response;
import com.example.case_study_car.domain.Specification;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class UserPricingResponse {
    private Long id;

    private String name;

    private String status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    private BigDecimal priceDelivery;

    private String seats;

    private String transmission;

    private String description;

    private String agency;

    private List<Specification> specifications;

    private List<String> features;

    private List<String> urlImages;


}
