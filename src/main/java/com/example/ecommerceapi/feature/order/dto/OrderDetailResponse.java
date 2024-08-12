package com.example.ecommerceapi.feature.order.dto;

import com.example.ecommerceapi.feature.product.dto.ProductOrderResponse;

public record OrderDetailResponse(
        Long id,
        String status,
        int quantity,
        double totalPrice,
        String OrderDetailNumber,
        ProductOrderResponse product
) {
}
