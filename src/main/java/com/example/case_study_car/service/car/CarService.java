package com.example.case_study_car.service.car;
import com.example.case_study_car.domain.*;

import com.example.case_study_car.exception.CarNotFoundException;
import com.example.case_study_car.repository.*;
import com.example.case_study_car.service.car.request.CarSaveRequest;
import com.example.case_study_car.service.car.response.*;
import com.example.case_study_car.service.car.response.CarDetailResponse;
import com.example.case_study_car.service.car.response.CarListResponse;
import com.example.case_study_car.service.car.response.CarShowDetailResponse;
import com.example.case_study_car.service.image.ImageResponse;
import com.example.case_study_car.service.image.ImageService;
import com.example.case_study_car.util.AppUtil;
import com.example.case_study_car.util.UploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.case_study_car.service.request.SelectOptionRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    private final CarFeatureRepository carFeatureRepository;

    private final CarSpecificationRepository carSpecificationRepository;

    private final ImageRepository imageRepository;

    private final ImageService uploadService;

    private final UploadUtil uploadUtil;

    public void create(CarSaveRequest request) {
        //api cho thằng specifications để vẽ ra trang manage car
        var car = AppUtil.mapper.map(request, Car.class);
        car = carRepository.save(car);

        var images = imageRepository.findAllById(request.getFiles().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var image: images) {
            image.setCar(car);
        }
        imageRepository.saveAll(images);

        Car finalCar = car;
        carSpecificationRepository.saveAll(request
                .getIdSpecifications()
                .stream()
                .map(id -> new CarSpecification(finalCar, new Specification(Long.valueOf(id))))
                .collect(Collectors.toList()));
        carFeatureRepository.saveAll(request
                .getIdFeatures()
                .stream()
                .map(id -> new CarFeature(finalCar, new Feature(Long.valueOf(id))))
                .collect(Collectors.toList()));
    }
    public CarDetailResponse findById(Long id) {
        var car = carRepository.findById(id).orElse(new Car());
        var result = AppUtil.mapper.map(car, CarDetailResponse.class);
        result.setAgencyId(car.getAgency().getId());
        result.setSpecificationIds(car
                .getCarSpecifications()
                .stream().map(carSpecification -> carSpecification.getSpecification().getId())
                .collect(Collectors.toList()));
        result.setFeatureIds(car
                .getCarFeatures()
                .stream().map(carFeature -> carFeature.getFeature().getId())
                .collect(Collectors.toList()));

        List<String> images = car.getImages()
                .stream()
                .map(Image::getFileUrl)
                .collect(Collectors.toList());
        result.setImages(images);

        return result;
    }
    public CarShowDetailResponse findCarDetailById(Long id) {
        var car = carRepository.findById(id).orElse(new Car());
        var result = AppUtil.mapper.map(car, CarShowDetailResponse.class);
        result.setAgencyName(car.getAgency().getName());
        result.setSpecificationNames(car
                .getCarSpecifications()
                .stream().map(carSpecification -> carSpecification.getSpecification().getName())
                .collect(Collectors.toList()));
        result.setFeatureNames(car
                .getCarFeatures()
                .stream().map(carFeature -> carFeature.getFeature().getName())
                .collect(Collectors.toList()));

        List<String> images = car.getImages()
                .stream()
                .map(Image::getFileUrl)
                .collect(Collectors.toList());
        result.setImages(images);

        System.out.println(result);
        return result;
    }
    public void update(CarSaveRequest request, Long id) {
        var carDb = carRepository.findById(id).orElse(new Car());
        carDb.setAgency(new Agency());
        AppUtil.mapper.map(request, carDb);
        carSpecificationRepository.deleteAll(carDb.getCarSpecifications());
        carFeatureRepository.deleteAll(carDb.getCarFeatures());

        for (Image image : carDb.getImages()) {
            imageRepository.delete(image);
        }

        var images = imageRepository.findAllById(request.getFiles().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var image: images) {
            image.setCar(carDb);
        }


        var carSpecifications = new ArrayList<CarSpecification>();
        for (String idSpecification : request.getIdSpecifications()) {
            Specification specification = new Specification(Long.valueOf(idSpecification));
            carSpecifications.add(new CarSpecification(carDb, specification));
        }

        var carFeatures = new ArrayList<CarFeature>();
        for (String idFeature : request.getIdFeatures()) {
            Feature feature = new Feature(Long.valueOf(idFeature));
            carFeatures.add(new CarFeature(carDb, feature));
        }

        carSpecificationRepository.saveAll(carSpecifications);
        carFeatureRepository.saveAll(carFeatures);
        imageRepository.saveAll(images);

        carRepository.save(carDb);
    }
    public void delete(Long id) {
        // Kiểm tra xem room có tồn tại trong cơ sở dữ liệu hay không
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();

            // Xóa tất cả các mối quan hệ với danh mục
            carSpecificationRepository.deleteAll(car.getCarSpecifications());
            carFeatureRepository.deleteAll(car.getCarFeatures());
            // Tạo vòng lặp xóa từng ảnh 1
            for (Image image : car.getImages()) {
                imageRepository.delete(image);
            }
            // Sau đó xóa toàn bộ car
            carRepository.deleteById(id);

        } else {
            //thông báo lỗi!!
            throw new CarNotFoundException("Xe không tồn tại với ID: " + id);
        }
    }
    public Page<CarListResponse> getAll(Pageable pageable, String search) {
        search = "%" + search + "%";
        return carRepository.searchEverything(search, pageable).map(e -> {
            var result = new CarListResponse();
            result.setName(e.getName());
            result.setId(e.getId());
            result.setDescription(e.getDescription());
            result.setStatus(e.getStatus().toString());
            result.setLicensePlate(e.getLicensePlate());
            result.setPriceHours(e.getPriceHours());
            result.setPriceDays(e.getPriceDays());
            result.setPriceDelivery(e.getPriceDelivery());
            result.setAgency(e.getAgency().getName());
//            result.setImage(e.getImages().get(0).getFileUrl());
            result.setImages(
                    e.getImages().stream()
                            .map(Image::getFileUrl)  // Lấy ra URL của mỗi ảnh
                            .collect(Collectors.toList())  // Tạo thành một danh sách
            );
            result.setSpecifications(e.getCarSpecifications()
                    .stream().map(s -> s.getSpecification().getName())
                    .collect(Collectors.joining(", ")));
            result.setFeatures(e.getCarFeatures()
                    .stream().map(f -> f.getFeature().getName())
                    .collect(Collectors.joining(", ")));
            return result;
        });
    }
    public List<BestCarResponse> getBestCars() {
        return carRepository.getBestCars().stream().map(car -> BestCarResponse.builder()
                .id(car.getId())
                .name(car.getName())
                .description(car.getDescription())
                .agency(car.getAgency().getName())
                .priceDays(car.getPriceDays())
//                .urlImages(car.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }
    public List<RelatedCarResponse> getRelatedCars(String agency, BigDecimal priceDay, String seat, Long id) {
        return carRepository.getRelatedCars(agency, seat, priceDay, id)
                .stream().map(car -> RelatedCarResponse.builder()
                        .id(car.getId())
                        .name(car.getName())
                        .description(car.getDescription())
                        .agency(car.getAgency().getName())
                        .priceDays(car.getPriceDays())
//                        .urlImages(car.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }
    public UserCarDetailResponse getCarDetailById(Long id) {
        var car = carRepository.findById(id).orElse(new Car());
        var result = AppUtil.mapper.map(car, UserCarDetailResponse.class);
        result.setAgency(car.getAgency().getName());
        result.setSpecifications(car
                .getCarSpecifications()
                .stream().map(carSpecification -> Specification.builder()
                        .name(carSpecification.getSpecification().getName())
                        .type(carSpecification.getSpecification().getType())
                        .svg(carSpecification.getSpecification().getSvg())
                        .build())
                .collect(Collectors.toList()));
        result.setSeats(car.getCarSpecifications()
                .stream()
                .filter(carSpecification -> carSpecification.getSpecification()
                        .getType().toString()
                        .equals("SEAT"))
                .findFirst()
                .map(spec -> spec.getSpecification()
                        .getName()).orElse(""));
        result.setTransmission(car.getCarSpecifications()
                .stream()
                .filter(carSpecification -> carSpecification.getSpecification()
                        .getType().toString()
                        .equals("TRANSMISSION"))
                .findFirst()
                .map(spec -> spec.getSpecification()
                        .getName()).orElse(""));
        result.setFeatures(car
                .getCarFeatures()
                .stream().map(carFeature -> carFeature.getFeature().getName())
                .collect(Collectors.toList()));

        result.setImages(
                car.getImages().stream()
                        .map(Image::getFileUrl)  // Lấy ra URL của mỗi ảnh
                        .collect(Collectors.toList())  // Tạo thành một danh sách
        );

        System.out.println(result);
        return result;
    }



    public Page<BestCarResponse> searchAvailableCar(Pageable pageable, LocalDateTime pickup, LocalDateTime dropOff, String search) {
        search = "%" + search + "%";
        return carRepository.searchAvailableCar(search, pickup, dropOff, pageable).map(car -> {
            var result = AppUtil.mapper.map(car, BestCarResponse.class);
            result.setAgency(car.getAgency().getName());
            result.setUrlImages(car
                    .getImages()
                    .stream().map(image -> image.getFileUrl())
                    .collect(Collectors.toList()));

            return result;
        });
    }
}
