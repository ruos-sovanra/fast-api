package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.domain.Role;
import com.example.ecommerceapi.domain.User;
import com.example.ecommerceapi.feature.user.dto.UserProfileResponse;
import com.example.ecommerceapi.feature.user.dto.UserRequest;
import com.example.ecommerceapi.feature.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "is_Deleted", source = "deleted")
    UserResponse toUserResponse(User user);
    @Mapping(target = "roles", source = "roleNames")
    User toUser(UserRequest userRequest);
    @Mapping(target = "orders", source = "orders")
    UserProfileResponse toUserProfileResponse(User user);

    default String map(List<String> value) {
        return String.join(",", value);
    }

    default List<String> mapRoles(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    default List<Role> mapRoleNames(List<String> roleNames) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return role;
                })
                .collect(Collectors.toList());
    }
}
