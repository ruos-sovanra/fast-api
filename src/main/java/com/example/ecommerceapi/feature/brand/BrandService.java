package com.example.ecommerceapi.feature.brand;

import com.example.ecommerceapi.feature.brand.dto.BrandRequest;
import com.example.ecommerceapi.feature.brand.dto.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse createBrand(BrandRequest request);
    BrandResponse getBrand(Long id);
    List<BrandResponse> getBrands();
    BrandResponse updateBrand(String uuid, BrandRequest request);
    void deleteBrand(String uuid);
}
