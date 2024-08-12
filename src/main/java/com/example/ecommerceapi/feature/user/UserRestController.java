package com.example.ecommerceapi.feature.user;

import com.example.ecommerceapi.feature.user.dto.UserProfileResponse;
import com.example.ecommerceapi.feature.user.dto.UserRequest;
import com.example.ecommerceapi.feature.user.dto.UserResponse;
import com.example.ecommerceapi.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public BaseResponse<List<UserResponse>> getUsers() {
        return BaseResponse.<List<UserResponse>>ok()
                .setPayload(userService.getUsers());
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "Get user profile")
    public BaseResponse<UserProfileResponse> getUserProfile(Long id) {
        return BaseResponse.<UserProfileResponse>ok()
                .setPayload(userService.getUserProfile(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public BaseResponse<UserResponse> getUser(Long id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.getUser(id));
    }



    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public BaseResponse<UserResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.updateUser(id, userRequest));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Enable user")
    public BaseResponse<UserResponse> enableUser(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.enableUser(id));
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Disable user")
    public BaseResponse<UserResponse> disableUser(@PathVariable Long id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.disableUser(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public BaseResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return BaseResponse.<Void>ok();
    }

}
