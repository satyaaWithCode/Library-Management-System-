package com.springBoot.reactive.config;

import com.springBoot.reactive.securities.JwtAuthEntryPoint;
import com.springBoot.reactive.securities.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableReactiveMethodSecurity  // <-- Enable role-based method-level security
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable()) //client call to add,put,delete with csrf token if we disable it will throw 403 forbidden//it will secure our security configuration  // react will not send  csrf but spring except a csrf that is why we have to disable otherwise it will display error
                .cors(cors -> cors.disable())//needed otherwise can communicate 5173 to 8081
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))//calling to commence if its unAuthenticate
                .authorizeExchange(auth -> auth
                        .pathMatchers("/auth/register", "/auth/login").permitAll() //anyone can register and login
                        .pathMatchers("/ws/notifications").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()//Your frontend (e.g., React app at http://localhost:5173) wants to call://The browser first sends a preflight request:before making add,delete,update like POST http://localhost:8080/auth/login to OPTIONS http://localhost:8080/auth/login
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION) //yeah it will give authentication after checking token inside the header
                .build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {  //you should never store plain-text passwords.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService, //it will load from db
            PasswordEncoder passwordEncoder //pw is correct or not
    )
    {
        var authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;  //loadUserByUsername using it will fetch the data from database
    }

}
