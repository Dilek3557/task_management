package com.dilekkaraca.taskmanagament.auth;

import com.dilekkaraca.taskmanagament.auth.dto.AuthResponse;
import com.dilekkaraca.taskmanagament.auth.dto.LoginRequest;
import com.dilekkaraca.taskmanagament.auth.dto.RegisterRequest;
import com.dilekkaraca.taskmanagament.user.Role;
import com.dilekkaraca.taskmanagament.user.User;
import com.dilekkaraca.taskmanagament.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public void register(RegisterRequest request) {
        // 1) email daha önce kullanılmış mı?
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Bu email zaten kayıtlı: " + request.email());
        }

        // 2) yeni kullanıcı oluştur
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());

        // 3) şifreyi HASH’le (DB’ye düz yazma!)
        user.setPassword(passwordEncoder.encode(request.password()));

        // 4) default rol ver
        user.getRoles().add(Role.ROLE_USER);

        // 5) kaydet
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email veya şifre hatalı"));

        // Şifre doğru mu? (düz şifreyi hash ile karşılaştırır)
        boolean passwordOk = passwordEncoder.matches(request.password(), user.getPassword());
        if (!passwordOk) {
            throw new IllegalArgumentException("Email veya şifre hatalı");
        }
        String token=jwtService.generateToken(user.getEmail());
        Set<String> roles = user.getRoles()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return new AuthResponse(token,user.getEmail(),roles);
    }
}