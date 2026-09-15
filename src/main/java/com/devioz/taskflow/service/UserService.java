package com.devioz.taskflow.service;

import com.devioz.taskflow.payload.response.UserResponse;
import com.devioz.taskflow.security.service.UserPrincipal;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Integer id);

    UserResponse getCurrentUser(UserPrincipal currentUser);
}
