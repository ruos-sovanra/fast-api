package com.example.ecommerceapi.feature.product.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductUpdateRequest(List<String> images) {
}
