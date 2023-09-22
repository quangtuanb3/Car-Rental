package com.example.case_study_car.controller.restUser;


import com.example.case_study_car.service.bill.BillService;
import com.example.case_study_car.service.bill.request.BillSaveRequest;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.car.response.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("user/api/cars")
@AllArgsConstructor
public class UserCarRestController {

    private final CarService carService;
    private final BillService billService;

    @GetMapping("/best-cars")
    public ResponseEntity<List<BestCarResponse>> getBestCars() {
        return new ResponseEntity<>(carService.getBestCars(), HttpStatus.OK);
    }


    @GetMapping("/related-cars/{id}/{agency}/{seat}/{priceDay}")
    public ResponseEntity<List<RelatedCarResponse>> getRelatedCars(@PathVariable String agency, @PathVariable String seat, @PathVariable BigDecimal priceDay, @PathVariable Long id) {
        return new ResponseEntity<>(carService.getRelatedCars(agency, priceDay, seat, id), HttpStatus.OK);
    }

    @GetMapping("car-detail/{id}")
    public ResponseEntity<UserCarDetailResponse> getCurrentCar(@PathVariable String id) {
        return new ResponseEntity<>(carService.getCarDetailById(Long.valueOf(id)), HttpStatus.OK);

    }


    @PostMapping("/rent")
    public void create(@RequestBody BillSaveRequest request) {
        billService.create(request);
    }

    @GetMapping("/available-cars")
    public ResponseEntity<Page<BestCarResponse>> getCars(@PageableDefault(size = 4) Pageable pageable,
                                                         @RequestParam(defaultValue = "") String search,
                                                         @RequestParam(defaultValue = "") LocalDateTime pickupTime,
                                                         @RequestParam(defaultValue = "") LocalDateTime dropOffTime) {
        return new ResponseEntity<>(carService.searchAvailableCar(pageable, pickupTime, dropOffTime, search), HttpStatus.OK);
    }

    @GetMapping("/check-available")
    public ResponseEntity<Boolean> checkAvailable(
            @RequestParam(defaultValue = "") Long id,
            @RequestParam(defaultValue = "") LocalDateTime pickupTime,
            @RequestParam(defaultValue = "") LocalDateTime dropOffTime) {
        return new ResponseEntity<>(carService.iskAvailable(id, pickupTime, dropOffTime), HttpStatus.OK);
    }

}
