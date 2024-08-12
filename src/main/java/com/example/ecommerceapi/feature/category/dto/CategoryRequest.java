package com.example.ecommerceapi.feature.category.dto;

import lombok.Builder;

@Builder
public record CategoryRequest(String name, String image) {
}
