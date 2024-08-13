package com.example.ecommerceapi.feature.brand;

import com.example.ecommerceapi.domain.Brand;
import com.example.ecommerceapi.feature.brand.dto.BrandRequest;
import com.example.ecommerceapi.feature.brand.dto.BrandResponse;
import com.example.ecommerceapi.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(BrandRequest request) {
        Brand brand = brandMapper.toBrand(request);
        brand.setUuid(UUID.randomUUID().toString());
        brandRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse getBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Brand not found"));
        brandRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public List<BrandResponse> getBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream().map(brandMapper::toBrandResponse).toList();
    }

    @Override
    public BrandResponse updateBrand(String uuid, BrandRequest request) {
        Brand brand = brandRepository.findByUuid(uuid).orElseThrow(()-> new NoSuchElementException("Brand not found"));
        brand.setName(request.name());
        brandRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public void deleteBrand(String uuid) {
    Brand brand = brandRepository.findByUuid(uuid).orElseThrow(()-> new NoSuchElementException("Brand not found"));

    brandRepository.delete(brand);
    }
}
