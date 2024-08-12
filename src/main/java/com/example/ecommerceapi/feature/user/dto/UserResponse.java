package com.example.ecommerceapi.feature.user.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(Long id, String userName, String email, String password, String profileImage, List<String> roles, boolean is_Deleted) {
}
