package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.domain.Brand;
import com.example.ecommerceapi.feature.brand.dto.BrandRequest;
import com.example.ecommerceapi.feature.brand.dto.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandResponse toBrandResponse(Brand brand);
    Brand toBrand(BrandRequest brandRequest);
}
