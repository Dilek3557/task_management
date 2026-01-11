package com.dilekkaraca.taskmanagament.config;

import com.dilekkaraca.taskmanagament.user.Role;
import com.dilekkaraca.taskmanagament.user.User;
import com.dilekkaraca.taskmanagament.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("123456"));

            admin.getRoles().add(Role.ROLE_ADMIN);
            admin.getRoles().add(Role.ROLE_USER);

            userRepository.save(admin);
        }
    }
}
