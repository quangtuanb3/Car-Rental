package com.example.case_study_car.uploader;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CloudinaryConfig.class)
public class UploaderConfig {
    private CloudinaryConfig config;
    public UploaderConfig(CloudinaryConfig config) {
        this.config = config;
    }
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", config.getCloudName(),
                "api_key", config.getApiKey(),
                "api_secret", config.getApiSecret()));
        return cloudinary;
    }
}