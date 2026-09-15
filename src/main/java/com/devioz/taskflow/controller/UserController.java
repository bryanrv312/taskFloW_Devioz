package com.devioz.taskflow.controller;

import com.devioz.taskflow.payload.response.ApiResponse;
import com.devioz.taskflow.payload.response.UserResponse;
import com.devioz.taskflow.security.service.UserPrincipal;
import com.devioz.taskflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para la gestión y consulta de usuarios protegidos con JWT")
@SecurityRequirement(name = "BearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Retorna la lista de todos los usuarios registrados en la base de datos (requiere autenticación JWT)")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ok(users, "Lista de usuarios obtenida exitosamente"));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener perfil del usuario autenticado", description = "Retorna los datos del usuario en sesión a partir de su Bearer Token")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserResponse user = userService.getCurrentUser(currentUser);
        return ResponseEntity.ok(ApiResponse.ok(user, "Perfil del usuario autenticado"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna los datos de un usuario por su ID numérico")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.ok(user, "Usuario encontrado"));
    }

    @GetMapping("/{emai/emaill}")
    @Operation(summary = "Obtener usuario por email", description = "Retorna los datos de un usuario por su correo electrónico")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(@PathVariable String email) {
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.ok(user, "Usuario encontrado"));
    }
}
