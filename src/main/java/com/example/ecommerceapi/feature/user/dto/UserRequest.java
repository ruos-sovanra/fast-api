package com.example.ecommerceapi.feature.user.dto;

import com.example.ecommerceapi.feature.password.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Builder
@Validated
@FieldMatch(first = "password", second = "confirm_password", message = "The password fields must match")
public record UserRequest(
        @NotEmpty
        String userName,
        @Email(message = "Email format is not correct!")
        String email,
        String password,
        String confirm_password,
        String profileImage,
        List<String> roleNames) {
}