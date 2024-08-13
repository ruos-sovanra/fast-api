package com.example.ecommerceapi.feature.product.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductResponse(
    Long id,
    String uuid,
    String name,
    String description,
    double price,
    int quantity,
    String categoryName,
    String brandName,
    String image
) {
}
