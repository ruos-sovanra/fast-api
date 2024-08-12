package com.example.ecommerceapi.feature.user;

import com.example.ecommerceapi.feature.user.dto.UserProfileResponse;
import com.example.ecommerceapi.feature.user.dto.UserRequest;
import com.example.ecommerceapi.feature.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUser(Long id);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    UserProfileResponse getUserProfile(Long id);
    UserResponse enableUser(Long id);
    UserResponse disableUser(Long id);

}
