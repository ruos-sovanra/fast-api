package com.example.ecommerceapi.feature.product.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductRequest(
    String name,
    String description,
    double price,
    int quantity,
    Long categoryId,
    Long brandId,
    List<String> images
) {
}
