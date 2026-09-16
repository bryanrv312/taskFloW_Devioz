package com.devioz.taskflow.mapper;

import com.devioz.taskflow.model.Role;
import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.request.RegisterRequest;
import com.devioz.taskflow.payload.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.nombre", target = "nombre")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.rol.nombre", target = "rol")
    @Mapping(source = "token", target = "token")
    @Mapping(constant = "Bearer", target = "tokenType")
    AuthResponse toAuthResponse(User user, String token);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "registerRequest.nombre", target = "nombre")
    @Mapping(source = "registerRequest.email", target = "email")
    @Mapping(source = "encodedPassword", target = "password")
    @Mapping(source = "rol", target = "rol")
    User toUser(RegisterRequest registerRequest, Role rol, String encodedPassword);
}
