package com.springBoot.reactive.service;

import com.springBoot.reactive.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> register(User user);  // Register new user

    Mono<User> findByEmail(String email); // Used for login/authentication

    Flux<User> getAllUsers();

    Mono<Void> createAdminIfNotExists();
}
