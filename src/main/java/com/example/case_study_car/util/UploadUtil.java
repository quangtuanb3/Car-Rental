package com.example.case_study_car.util;
import com.cloudinary.utils.ObjectUtils;
import com.example.case_study_car.domain.Image;
import com.example.case_study_car.exception.DataInputException;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class UploadUtil {
    public static final String IMAGE_UPLOAD_FOLDER = "car_rental";
    public Map buildImageUploadParams(Image image) {
        if (image == null || image.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, image.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }
}
