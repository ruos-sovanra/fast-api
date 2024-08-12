package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.domain.Product;
import com.example.ecommerceapi.feature.product.dto.ProductOrderResponse;
import com.example.ecommerceapi.feature.product.dto.ProductRequest;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandName", source = "brand.name")
    ProductResponse toProductResponse(Product product);
    ProductOrderResponse toProductOrderResponse(Product product);
    Product toProduct(ProductRequest productRequest);

    default List<String> map(String value) {
        return Arrays.asList(value.split(","));
    }

    default String map(List<String> value) {
        return String.join(",", value);
    }
}
