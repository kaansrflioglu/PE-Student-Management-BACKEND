package com.kaansrflioglu.pe.controller;

import com.kaansrflioglu.pe.model.User;
import com.kaansrflioglu.pe.repository.UserRepository;
import com.kaansrflioglu.pe.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Bu kullanıcı adı zaten mevcut");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("PENDING");
        userRepository.save(user);
        return "Kullanıcı oluşturuldu, onay bekleniyor.";
    }


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Hatalı giriş");
        }

        if ("PENDING".equals(dbUser.getRole())) {
            throw new RuntimeException("Hesabınız onay bekliyor");
        }

        String token = jwtService.generateToken(dbUser);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", dbUser.getRole());
        return response;
    }
}