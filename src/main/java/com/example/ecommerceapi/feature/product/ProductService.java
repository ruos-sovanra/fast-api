package com.example.ecommerceapi.feature.product;

import com.example.ecommerceapi.feature.product.dto.ProductRequest;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import com.example.ecommerceapi.feature.product.dto.ProductUpdateRequest;
import com.example.ecommerceapi.utils.CustomPageUtils;

import java.util.List;

public interface ProductService {
    CustomPageUtils<ProductResponse> getAllProducts(int page, int size);
    ProductResponse getProductByName(String name);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
    ProductResponse updateProductImage(Long id, ProductUpdateRequest request);
}
