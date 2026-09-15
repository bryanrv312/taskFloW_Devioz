package com.devioz.taskflow.config;

import com.devioz.taskflow.model.Role;
import com.devioz.taskflow.model.User;
import com.devioz.taskflow.repository.RoleRepository;
import com.devioz.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 1. Crear roles por defecto si no existen
        if (roleRepository.count() == 0) {
            log.info("Inicializando roles por defecto en la base de datos...");
            roleRepository.save(Role.builder().nombre("ROLE_USER").build());
            roleRepository.save(Role.builder().nombre("ROLE_ADMIN").build());
            log.info("Roles ROLE_USER y ROLE_ADMIN creados exitosamente.");
        }

        // 2. Crear usuario inicial si la tabla usuarios está vacía
        if (userRepository.count() == 0) {
            log.info("Inicializando usuario administrador por defecto...");
            Role adminRole = roleRepository.findByNombre("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.findAll().get(0));

            User admin = User.builder()
                    .nombre("Administrador Devioz")
                    .email("admin@devioz.com")
                    .password(passwordEncoder.encode("admin123"))
                    .rol(adminRole)
                    .build();

            userRepository.save(admin);
            log.info("Usuario creado: admin@devioz.com / admin123");
        }
    }
}
