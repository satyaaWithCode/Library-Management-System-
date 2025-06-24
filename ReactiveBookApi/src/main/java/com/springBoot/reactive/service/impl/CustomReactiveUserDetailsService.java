package com.springBoot.reactive.service.impl;

import com.springBoot.reactive.repositories.ReactiveUserRepository;
import com.springBoot.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private ReactiveUserRepository userRepository;

//    This class tells Spring how to load a user from your database when someone tries to log in.
    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + email)))
                .map(u -> User
                        .withUsername(u.getEmail())
                        .password(u.getPassword())
                        .roles(u.getRole().replace("ROLE_", "")) // Spring adds "ROLE_" prefix automatically
                        .build()
                );
    }
}
