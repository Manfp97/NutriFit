package com.eoi.NutriFit.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")  // URL para el logout
                .logoutSuccessUrl("/") // Redirige a la página principal después del logout
                .invalidateHttpSession(true) // Invalidar la sesión HTTP
                .deleteCookies("JSESSIONID") // Eliminar cookies
        );

        http.authorizeHttpRequests(customizer -> {
            customizer
                    .requestMatchers("/js/**").permitAll()
                    .requestMatchers("/img/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/fonts/**").permitAll()
                    .requestMatchers("/static/lib/**").permitAll()
                    .requestMatchers("/static/scss/**").permitAll()
                    .requestMatchers("/index").permitAll(); // Permitir todas las solicitudes por defecto
            customizer.anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }
}