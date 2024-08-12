package com.example.ecommerceapi.feature.order.dto;

import com.example.ecommerceapi.feature.product.dto.ProductOrderResponse;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponse(
        Long id,
        String status,
        String addressId,
        int quantity,
        double totalPrice,
        String OrderDetailNumber,
        ProductOrderResponse product,
        String userName
) {
}
