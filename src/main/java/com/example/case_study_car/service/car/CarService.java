package com.example.case_study_car.service.car;

import com.example.case_study_car.domain.*;
import com.example.case_study_car.domain.enumaration.ESpecificationType;
import com.example.case_study_car.exception.CarNotFoundException;
import com.example.case_study_car.repository.*;
import com.example.case_study_car.service.car.request.CarSaveRequest;

import com.example.case_study_car.service.car.response.*;

import com.example.case_study_car.service.car.response.CarDetailResponse;
import com.example.case_study_car.service.car.response.CarListResponse;
import com.example.case_study_car.service.car.response.CarShowDetailResponse;
import com.example.case_study_car.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    private final CarFeatureRepository carFeatureRepository;

    private final CarSurchargeRepository carSurchargeRepository;

    private final CarSpecificationRepository carSpecificationRepository;

    private final ImageRepository imageRepository;




    public void create(CarSaveRequest request){
        var car = AppUtil.mapper.map(request, Car.class);
        car = carRepository.save(car);

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
        carSurchargeRepository.saveAll(request
                .getIdSurcharges()
                .stream()
                .map(id -> new CarSurcharge(finalCar, new Surcharge(Long.valueOf(id))))
                .collect(Collectors.toList()));
        imageRepository.saveAll(request
                .getUrlImages()
                .stream()
                .map(url -> new Image(url, finalCar))
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
        result.setSurchargeIds(car
                .getCarSurcharges()
                .stream().map(carSurcharge -> carSurcharge.getSurcharge().getId())
                .collect(Collectors.toList()));
        result.setUrlImages(car
                .getImages()
                .stream().map(Image::getUrl)
                .collect(Collectors.toList()));

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
        result.setSurchargeNames(car
                .getCarSurcharges()
                .stream().map(carSurcharge -> carSurcharge.getSurcharge().getName())
                .collect(Collectors.toList()));
        result.setUrlImages(car
                .getImages()
                .stream().map(Image::getUrl)
                .collect(Collectors.toList()));
        return result;
    }


    public Page<CarListResponse> getCars(Pageable pageable, String search) {
        search = "%" + search + "%";
        return carRepository.searchEverything(search, pageable).map(e -> {
            var result = AppUtil.mapper.map(e, CarListResponse.class);
            result.setAgency(e.getAgency().getName());
            result.setSpecifications(e.getCarSpecifications()
                    .stream().map(s -> s.getSpecification().getName())
                    .collect(Collectors.joining(", ")));
            result.setFeatures(e.getCarFeatures()
                    .stream().map(f -> f.getFeature().getName())
                    .collect(Collectors.joining(", ")));
            result.setSurcharges(e.getCarSurcharges()
                    .stream().map(u -> u.getSurcharge().getName())
                    .collect(Collectors.joining(", ")));
            result.setUrlImages(e.getImages()
                    .stream().map(Image::getUrl)
                    .collect(Collectors.joining(", ")));
            return result;
        });
    }


//    public Page<CarListResponse> getCars(Pageable pageable, String search){
//        search = "%" + search + "%";
//        return carRepository.searchEverything(search ,pageable).map(e -> {
//            var result = AppUtil.mapper.map(e, CarListResponse.class);
//            result.setAgency(e.getAgency().getName());
//            result.setSpecifications(e.getCarSpecifications()
//                    .stream().map(s -> s.getSpecification().getName())
//                    .collect(Collectors.joining(", ")));
//            result.setFeatures(e.getCarFeatures()
//                    .stream().map(f -> f.getFeature().getName())
//                    .collect(Collectors.joining(", ")));
//            result.setSurcharges(e.getCarSurcharges()
//                    .stream().map(u -> u.getSurcharge().getName())
//                    .collect(Collectors.joining(", ")));
//            result.setUrlImages(e.getImages()
//                    .stream().map(Image::getUrl)
//                    .collect(Collectors.joining(", ")));
//            return result;
//        });
//    }

    public void update(CarSaveRequest request, Long id){
        var carDb = carRepository.findById(id).orElse(new Car());
        carDb.setAgency(new Agency());
        AppUtil.mapper.map(request, carDb);
        carSpecificationRepository.deleteAll(carDb.getCarSpecifications());
        carFeatureRepository.deleteAll(carDb.getCarFeatures());
        carSurchargeRepository.deleteAll(carDb.getCarSurcharges());
        imageRepository.deleteAll(carDb.getImages());


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

        var carSurcharges = new ArrayList<CarSurcharge>();
        for (String idSurcharge : request.getIdSurcharges()) {
            Surcharge surcharge = new Surcharge(Long.valueOf(idSurcharge));
            carSurcharges.add(new CarSurcharge(carDb, surcharge));
        }

        var images = new ArrayList<Image>();
        for (String idImage : request.getUrlImages()) {
            Image image = new Image(); // Tạo một đối tượng Image mới
            image.setUrl(idImage); // Gán URL cho đối tượng Image
            image.setCar(carDb); // Gán Car cho đối tượng Image
            images.add(image);
        }

        carSpecificationRepository.saveAll(carSpecifications);
        carFeatureRepository.saveAll(carFeatures);
        carSurchargeRepository.saveAll(carSurcharges);
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
            carSurchargeRepository.deleteAll(car.getCarSurcharges());
            imageRepository.deleteAll(car.getImages());


            // Sau đó xóa bộ room
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
            result.setAgency(e.getAgency().getName());
            result.setSpecifications(e.getCarSpecifications()
                    .stream().map(s -> s.getSpecification().getName())
                    .collect(Collectors.joining(", ")));

            // Lọc danh sách CarSpecification theo loại specificationType
//            List<CarSpecification> filteredSpecifications = e.getCarSpecifications()
//                    .stream()
//                    .filter(carSpecification -> carSpecification.getSpecification().getType() == specificationType)
//                    .collect(Collectors.toList());

            // Lấy danh sách các Specification từ danh sách CarSpecification
//            List<Specification> specifications = filteredSpecifications
//                    .stream()
//                    .map(CarSpecification::getSpecification)
//                    .collect(Collectors.toList());

//            result.setSpecifications(specifications);


            result.setFeatures(e.getCarFeatures()
                    .stream().map(f -> f.getFeature().getName())
                    .collect(Collectors.joining(", ")));
            result.setSurcharges(e.getCarSurcharges()
                    .stream().map(u -> u.getSurcharge().getName())
                    .collect(Collectors.joining(", ")));
            result.setUrlImages(e.getImages()
                    .stream().map(Image::getUrl)
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
                .urlImages(car.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }
    public List<RelatedCarResponse> getRelatedCars(String agency, BigDecimal priceDay, String seat, Long id){
        return carRepository.getRelatedCars(agency, seat, priceDay, id)
                .stream().map(car -> RelatedCarResponse.builder()
                .id(car.getId())
                .name(car.getName())
                .description(car.getDescription())
                .agency(car.getAgency().getName())
                .priceDays(car.getPriceDays())
                .urlImages(car.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
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
        result.setFeatures(car
                .getCarFeatures()
                .stream().map(carFeature -> carFeature.getFeature().getName())
                .collect(Collectors.toList()));
        result.setUrlImages(car
                .getImages()
                .stream().map(Image::getUrl)
                .collect(Collectors.toList()));
        return result;
    }
}
