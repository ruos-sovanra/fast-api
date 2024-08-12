package com.example.ecommerceapi.feature.review.dto;

public record ReviewRequest(
        String comment,
        Integer rating,
        Long productId,
        Long userId
) {
}
