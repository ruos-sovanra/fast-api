package com.example.ecommerceapi.feature.user.dto;

import com.example.ecommerceapi.feature.order.dto.OrderDetailResponse;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileResponse(
        Long id,
        String userName,
        List<OrderDetailResponse> orders
) {
}
