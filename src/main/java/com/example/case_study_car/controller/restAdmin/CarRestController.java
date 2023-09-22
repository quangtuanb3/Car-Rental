package com.example.case_study_car.controller.restAdmin;


import com.example.case_study_car.domain.enumaration.ECarStatus;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cars")
@AllArgsConstructor
public class CarRestController {

    private final CarService carService;

    @PostMapping
    public void create(@RequestBody CarSaveRequest request){
        carService.create(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDetailResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(carService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<CarShowDetailResponse> findCarDetail(@PathVariable Long id){
        return new ResponseEntity<>(carService.findCarDetailById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CarListResponse>> getCars(@PageableDefault(size = 5) Pageable pageable,
                                                          @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(carService.getAll(pageable, search), HttpStatus.OK);
    }
//    @GetMapping
//    public ResponseEntity<List<CarListResponse>> getCars(){
//        return new ResponseEntity<>(carService.getAll(), HttpStatus.OK);
//    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCar(@RequestBody CarSaveRequest request, @PathVariable Long id){
        carService.update(request,id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/status")
    public ResponseEntity<ECarStatus[]> getStatus() {
        return new ResponseEntity<>(ECarStatus.values(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/softdelete")
    public ResponseEntity<?> softDeleteCar(@PathVariable Long id) {
        carService.softDeleteCar(id);
        return ResponseEntity.ok("Xe đã được xóa mềm thành công.");
    }

}
