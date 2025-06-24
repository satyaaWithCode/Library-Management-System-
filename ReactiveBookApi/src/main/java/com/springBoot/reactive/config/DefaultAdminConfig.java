package com.springBoot.reactive.config;

import com.springBoot.reactive.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultAdminConfig {
//used for set inMemory admin gmail and pw
    @Bean
    public CommandLineRunner setupDefaultAdmin(UserService userService) {
        return args -> {
            userService.createAdminIfNotExists().subscribe();  //create default admin
        };
    }
}
