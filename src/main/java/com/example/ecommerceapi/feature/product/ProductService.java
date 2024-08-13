package com.example.ecommerceapi.feature.product;

import com.example.ecommerceapi.feature.product.dto.ProductRequest;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import com.example.ecommerceapi.feature.product.dto.ProductUpdateRequest;
import com.example.ecommerceapi.utils.CustomPageUtils;

import java.util.List;

public interface ProductService {
    CustomPageUtils<ProductResponse> getAllProducts(int page, int size);
    ProductResponse getProductByName(String uuid);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(String uuid, ProductRequest productRequest);
    void deleteProduct(String uuid);
    ProductResponse updateProductImage(Long id, ProductUpdateRequest request);
}
