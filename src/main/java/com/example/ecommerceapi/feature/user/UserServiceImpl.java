package com.example.ecommerceapi.feature.user;

import com.example.ecommerceapi.domain.Role;
import com.example.ecommerceapi.domain.User;
import com.example.ecommerceapi.feature.user.dto.UserProfileResponse;
import com.example.ecommerceapi.feature.user.dto.UserRequest;
import com.example.ecommerceapi.feature.user.dto.UserResponse;
import com.example.ecommerceapi.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EntityManager entityManager;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.password()));
        user.setConfirm_password(new BCryptPasswordEncoder().encode(userRequest.confirm_password()));
        Role role = roleRepository.findByName(userRequest.roleNames().get(0))
                .orElseThrow(()-> new NoSuchElementException("Role Not Found"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        Role role = roleRepository.findByName(userRequest.roleNames().get(0))
                .orElseThrow(()-> new NoSuchElementException("Role Not Found"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        userMapper.toUser(userRequest);
        user.setUserName(userRequest.userName());
        user.setEmail(userRequest.email());
        user.setRoles(roles);
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.password()));
        user.setConfirm_password(new BCryptPasswordEncoder().encode(userRequest.confirm_password()));
        user.setProfileImage(userRequest.profileImage());
        var updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserProfileResponse getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    @Transactional
    public UserResponse enableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        userRepository.enableUser(id);
        User updatedUser = userRepository.save(user);
        entityManager.refresh(user);
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public UserResponse disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        userRepository.disableUser(id);
        User updatedUser = userRepository.save(user);
        entityManager.refresh(user);
        return userMapper.toUserResponse(updatedUser);
    }
}