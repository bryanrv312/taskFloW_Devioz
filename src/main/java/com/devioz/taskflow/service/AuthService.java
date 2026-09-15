package com.devioz.taskflow.service;

import com.devioz.taskflow.payload.request.LoginRequest;
import com.devioz.taskflow.payload.request.RegisterRequest;
import com.devioz.taskflow.payload.response.ApiResponse;
import com.devioz.taskflow.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    ApiResponse<String> register(RegisterRequest registerRequest);
}
