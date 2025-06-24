package com.springBoot.reactive.controllers;


import com.springBoot.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import com.springBoot.reactive.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
