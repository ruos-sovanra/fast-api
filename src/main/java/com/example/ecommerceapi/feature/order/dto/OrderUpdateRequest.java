package com.example.ecommerceapi.feature.order.dto;

import lombok.Builder;

@Builder
public record OrderUpdateRequest(
        String status
) {
}
