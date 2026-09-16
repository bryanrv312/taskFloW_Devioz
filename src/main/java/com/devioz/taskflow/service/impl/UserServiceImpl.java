package com.devioz.taskflow.service.impl;

import com.devioz.taskflow.exception.ResourceNotFoundException;
import com.devioz.taskflow.mapper.UserMapper;
import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.response.UserResponse;
import com.devioz.taskflow.repository.UserRepository;
import com.devioz.taskflow.security.service.UserPrincipal;
import com.devioz.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByNombre(String nombre) {
        User user = userRepository.findFirstByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "nombre", nombre));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", currentUser.getId()));
        return userMapper.toResponse(user);
    }
}
