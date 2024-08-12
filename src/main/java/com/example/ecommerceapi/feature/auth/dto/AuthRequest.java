package com.example.ecommerceapi.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthRequest(
        String email,
        String password
) {
}
