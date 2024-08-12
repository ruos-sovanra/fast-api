package com.example.ecommerceapi.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${file_storage.image_location}")
    String fileStorageLocation;
    @Value("${file_storage.client_path}")
    String clientLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientLocation)
                .addResourceLocations("file:"+fileStorageLocation);

    }
}
