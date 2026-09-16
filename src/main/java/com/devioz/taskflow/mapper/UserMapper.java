package com.devioz.taskflow.mapper;

import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "rol.nombre", target = "rol")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
