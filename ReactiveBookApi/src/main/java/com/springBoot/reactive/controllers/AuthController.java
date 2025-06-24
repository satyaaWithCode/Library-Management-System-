package com.springBoot.reactive.controllers;

import com.springBoot.reactive.DTO.AuthRequest;
import com.springBoot.reactive.DTO.AuthResponse;
import com.springBoot.reactive.entity.User;
import com.springBoot.reactive.securities.JwtHelper;
import com.springBoot.reactive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private  UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@Valid @RequestBody User user) {
        return userService.register(user)
                .map(u -> ResponseEntity.ok("User registered successfully with email: " + u.getEmail()))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("Registration error: " + e.getMessage())));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        return userService.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    String token = jwtHelper.generateToken(user.getEmail(),user.getRole());
                    return ResponseEntity.ok(new AuthResponse(token, user.getRole(), "Login successful"));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(401).body(new AuthResponse(null,null, "Invalid email or password"))));
    }
}
