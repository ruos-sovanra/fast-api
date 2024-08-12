package com.example.ecommerceapi.feature.brand;


import com.example.ecommerceapi.feature.brand.dto.BrandRequest;
import com.example.ecommerceapi.feature.brand.dto.BrandResponse;
import com.example.ecommerceapi.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/brands")
public class BrandRestController {
    private final BrandService brandService;

    @GetMapping
    @Operation(summary = "Get all brands")
    public BaseResponse<List<BrandResponse>> getBrands(){
        return BaseResponse.<List<BrandResponse>>ok()
                .setPayload(brandService.getBrands());
    }

    @PostMapping
    @Operation(summary = "Create a brand")
    public BaseResponse<BrandResponse> createBrand(@Valid @RequestBody BrandRequest request){
        return BaseResponse.<BrandResponse>createSuccess()
                .setPayload(brandService.createBrand(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a brand by id")
    public BaseResponse<BrandResponse> getBrand(@PathVariable Long id){
        return BaseResponse.<BrandResponse>ok()
                .setPayload(brandService.getBrand(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a brand by id")
    public BaseResponse<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandRequest request){
        return BaseResponse.<BrandResponse>ok()
                .setPayload(brandService.updateBrand(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a brand by id")
    public BaseResponse<Void> deleteBrand(@PathVariable Long id){
        brandService.deleteBrand(id);
        return BaseResponse.<Void>ok();
    }
}
