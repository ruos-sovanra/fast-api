package com.example.ecommerceapi.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        Long userId,
        String accessToken,
        String refreshToken
) {
}
