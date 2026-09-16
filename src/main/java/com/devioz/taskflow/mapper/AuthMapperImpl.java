package com.devioz.taskflow.mapper;

import com.devioz.taskflow.model.Role;
import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.request.RegisterRequest;
import com.devioz.taskflow.payload.response.AuthResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthResponse toAuthResponse(User user, String token) {
        if (user == null && token == null) {
            return null;
        }

        String rol = null;
        Integer id = null;
        String nombre = null;
        String email = null;

        if (user != null) {
            id = user.getId();
            nombre = user.getNombre();
            email = user.getEmail();
            if (user.getRol() != null) {
                rol = user.getRol().getNombre();
            }
        }

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .id(id)
                .nombre(nombre)
                .email(email)
                .rol(rol)
                .build();
    }

    @Override
    public User toUser(RegisterRequest registerRequest, Role rol, String encodedPassword) {
        if (registerRequest == null && rol == null && encodedPassword == null) {
            return null;
        }

        User.UserBuilder user = User.builder();
        if (registerRequest != null) {
            user.nombre(registerRequest.getNombre());
            user.email(registerRequest.getEmail());
        }
        user.password(encodedPassword);
        user.rol(rol);

        return user.build();
    }
}
