package com.example.case_study_car.service.image;
import com.example.case_study_car.domain.Image;
import com.example.case_study_car.repository.ImageRepository;
import com.example.case_study_car.repository.SpecificationRepository;
import com.example.case_study_car.service.response.SelectOptionResponse;
import com.example.case_study_car.util.UploadUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageService {

    private final Cloudinary cloudinary;

    private final ImageRepository imageRepository;

    private final UploadUtil uploadUtil;

    public Image saveAvatar(MultipartFile avatar) throws IOException {
        var image = new Image();
        imageRepository.save(image);

        var uploadResult = cloudinary.uploader().upload(avatar.getBytes(), uploadUtil.buildImageUploadParams(image));

        String fileUrl = (String) uploadResult.get("secure_url");
        String fileFormat = (String) uploadResult.get("format");

        image.setFileName(image.getId() + "." + fileFormat);
        image.setFileUrl(fileUrl);
        image.setFileFolder(UploadUtil.IMAGE_UPLOAD_FOLDER);
        image.setCloudId(image.getFileFolder() + "/" + image.getId());
        imageRepository.save(image);
        return image;
    }




}
