package com.example.case_study_car.controller.restBill;


import com.example.case_study_car.domain.Bill;
import com.example.case_study_car.domain.enumaration.EBillStatus;
import com.example.case_study_car.domain.enumaration.ECarStatus;
import com.example.case_study_car.service.bill.BillService;
import com.example.case_study_car.service.bill.request.BillSaveRequest;
import com.example.case_study_car.service.bill.response.BillDetailResponse;
import com.example.case_study_car.service.bill.response.BillListResponse;
import com.example.case_study_car.service.bill.response.BillShowDetailResponse;
import com.example.case_study_car.service.car.CarService;
import com.example.case_study_car.service.car.request.CarSaveRequest;
import com.example.case_study_car.service.car.response.CarDetailResponse;
import com.example.case_study_car.service.car.response.CarListResponse;
import com.example.case_study_car.service.car.response.CarShowDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/bills")
@AllArgsConstructor
public class BillRestController {

    private final BillService billService;

    @PostMapping
    public void create(@RequestBody BillSaveRequest request){
        billService.create(request);
    }



//    @GetMapping
//    public ResponseEntity<Page<BillListResponse>> getBills(@PageableDefault(size = 5) Pageable pageable
//                                                          ) {
//        return new ResponseEntity<>(billService.getBills(pageable), HttpStatus.OK);
//    }


//    @GetMapping("/status")
//    public ResponseEntity<ECarStatus[]> getStatus() {
//        return new ResponseEntity<>(ECarStatus.values(), HttpStatus.OK);
//    }

    @GetMapping
    public List<BillListResponse> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDetailResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(billService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<BillShowDetailResponse> findCarDetail(@PathVariable Long id){
        return new ResponseEntity<>(billService.findCarDetailById(id), HttpStatus.OK);
    }

    @GetMapping("/bill-status")
    public ResponseEntity<EBillStatus[]> getBillStatus() {
        return new ResponseEntity<>(EBillStatus.values(), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<Page<BillListResponse>> getBills(@PageableDefault(size = 5) Pageable pageable
//    ) {
//        return new ResponseEntity<>(billService.getPageBills(pageable), HttpStatus.OK);
//    }

//    @GetMapping("/{id}/{bill-status}")
//    public String changeStatus(@PathVariable Long id, @PathVariable EBillStatus status){
//        billService.changeStatus(id, status);
//        return "redirect:/bill?message=Change Success";
//    }

    @GetMapping("/{id}/change-status")
    public ResponseEntity<String> changeStatus(@PathVariable Long id, @RequestParam EBillStatus status) {
        try {
            billService.changeStatus(id, status);
            return ResponseEntity.ok("Change Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
