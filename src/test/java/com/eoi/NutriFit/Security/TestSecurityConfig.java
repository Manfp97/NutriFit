package com.eoi.NutriFit.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desactivar CSRF para las pruebas
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/producto/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/producto/**").permitAll()
                        .anyRequest().permitAll()  // Permitir todas las solicitudes
                );
        return http.build();
    }
}
