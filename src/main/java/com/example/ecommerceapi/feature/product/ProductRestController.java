package com.example.ecommerceapi.feature.product;


import com.example.ecommerceapi.feature.category.dto.CategoryResponse;
import com.example.ecommerceapi.feature.product.dto.ProductRequest;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import com.example.ecommerceapi.feature.product.dto.ProductUpdateRequest;
import com.example.ecommerceapi.utils.BaseResponse;
import com.example.ecommerceapi.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<CustomPageUtils<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size){
        CustomPageUtils<ProductResponse> postResponseCustomPage = productService.getAllProducts(page, size);
        return ResponseEntity.ok(postResponseCustomPage);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get product by name")
    public BaseResponse<ProductResponse> getProductByName(String name){
        return BaseResponse.<ProductResponse>ok()
                .setPayload(productService.getProductByName(name));
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public BaseResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        return BaseResponse.<ProductResponse>createSuccess()
                .setPayload(productService.createProduct(productRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public BaseResponse<ProductResponse> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest productRequest){
        return BaseResponse.<ProductResponse>ok()
                .setPayload(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public BaseResponse<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return BaseResponse.<String>ok()
                .setPayload("Product deleted successfully");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a product image")
    public BaseResponse<ProductResponse> updateProductImage(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        return BaseResponse.<ProductResponse>ok()
                .setPayload(productService.updateProductImage(id, request));
    }




}
