package com.springBoot.reactive.service.impl;

import com.springBoot.reactive.entity.User;
import com.springBoot.reactive.repositories.ReactiveUserRepository;
import com.springBoot.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ReactiveUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> register(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.<User>error(new RuntimeException("User already exists with email: " + user.getEmail())))
                .switchIfEmpty(Mono.defer(() -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return userRepository.save(user);
                }));
    }


    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);  // <-- Return actual repo method
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Void> createAdminIfNotExists() {
        String adminEmail = "satya341@gmail.com";
        String adminPassword = "satya99";
        return userRepository.findByEmail(adminEmail)
                .switchIfEmpty(
                        Mono.defer(() -> {
        String adminName = "Admin";

                            User admin = new User();
                            admin.setName(adminName);
                            admin.setEmail(adminEmail);
                            admin.setPassword(passwordEncoder.encode(adminPassword));
                            admin.setRole("ROLE_ADMIN");
                            return userRepository.save(admin);
                        })
                )
                .then(); // return Mono<Void>
    }



}
