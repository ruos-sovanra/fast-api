package com.example.ecommerceapi.feature.auth;

import com.example.ecommerceapi.feature.auth.dto.AuthRequest;
import com.example.ecommerceapi.feature.auth.dto.AuthResponse;
import com.example.ecommerceapi.feature.auth.dto.RefreshTokenRequest;
import com.example.ecommerceapi.feature.user.UserService;
import com.example.ecommerceapi.feature.user.dto.UserRequest;
import com.example.ecommerceapi.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@SecurityRequirements(value = {})
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthServiceImpl authService;
    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.login(request));
    }

    @PostMapping("/refresh")
    public BaseResponse<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.refreshToken(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Create user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity.ok(BaseResponse.createSuccess()
                    .setPayload(userService.createUser(userRequest)));
        } catch (ConstraintViolationException ex) {
            HashMap<String, Object> errors = new HashMap<>();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(BaseResponse.badRequest().setMetadata(errors));
        }
    }
}
