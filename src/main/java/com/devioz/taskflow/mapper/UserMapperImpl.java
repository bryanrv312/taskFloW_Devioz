package com.devioz.taskflow.mapper;

import com.devioz.taskflow.model.User;
import com.devioz.taskflow.payload.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        String rol = null;
        if (user.getRol() != null) {
            rol = user.getRol().getNombre();
        }

        return UserResponse.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .rol(rol)
                .build();
    }

    @Override
    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return null;
        }
        List<UserResponse> list = new ArrayList<>(users.size());
        for (User user : users) {
            list.add(toResponse(user));
        }
        return list;
    }
}
