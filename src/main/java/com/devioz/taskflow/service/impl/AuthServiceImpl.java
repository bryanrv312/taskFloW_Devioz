package com.devioz.taskflow.service.impl;

import com.devioz.taskflow.exception.BadRequestException;
import com.devioz.taskflow.exception.ResourceNotFoundException;
import com.devioz.taskflow.model.Role;
import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.request.LoginRequest;
import com.devioz.taskflow.payload.request.RegisterRequest;
import com.devioz.taskflow.payload.response.ApiResponse;
import com.devioz.taskflow.payload.response.AuthResponse;
import com.devioz.taskflow.repository.RoleRepository;
import com.devioz.taskflow.repository.UserRepository;
import com.devioz.taskflow.security.jwt.JwtTokenProvider;
import com.devioz.taskflow.security.service.UserPrincipal;
import com.devioz.taskflow.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userPrincipal.getId()));

        return AuthResponse.builder()
                .token(jwt)
                .tokenType("Bearer")
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .rol(user.getRol().getNombre())
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("El correo electrónico '" + registerRequest.getEmail() + "' ya está registrado");
        }

        Role rol;
        if (registerRequest.getRolId() != null) {
            rol = roleRepository.findById(registerRequest.getRolId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rol", "id", registerRequest.getRolId()));
        } else {
            // Si no se especifica rolId, busca el primer rol disponible en la tabla roles
            rol = roleRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new BadRequestException("No existen roles configurados en la base de datos"));
        }

        User user = User.builder()
                .nombre(registerRequest.getNombre())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .rol(rol)
                .build();

        userRepository.save(user);

        return ApiResponse.ok("Usuario registrado exitosamente");
    }
}
