package com.example.ecommerceapi.feature.review.dto;

public record ReviewResponse(
        Long id,
        String comment,
        Integer rating,
        String productName,
        String userName
) {
}
