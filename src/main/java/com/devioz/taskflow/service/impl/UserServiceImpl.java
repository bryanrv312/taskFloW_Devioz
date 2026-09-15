package com.devioz.taskflow.service.impl;

import com.devioz.taskflow.exception.ResourceNotFoundException;
import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.response.UserResponse;
import com.devioz.taskflow.repository.UserRepository;
import com.devioz.taskflow.security.service.UserPrincipal;
import com.devioz.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", currentUser.getId()));
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .rol(user.getRol().getNombre())
                .build();
    }
}
