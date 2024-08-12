package com.example.ecommerceapi.feature.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(Long id, String name, String image) {
}
