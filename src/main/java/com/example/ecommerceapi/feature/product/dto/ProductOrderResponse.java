package com.example.ecommerceapi.feature.product.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductOrderResponse(
        Long id,
        String name,
        String image,
        double price,String description) {
}
