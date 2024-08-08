package com.eoi.NutriFit.Security;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig <S extends Session>{

    private final FindByIndexNameSessionRepository<Session> sessionRepository;

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, FindByIndexNameSessionRepository<Session> sessionRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
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

        http.rememberMe((rememberMe) -> rememberMe
                        .rememberMeServices(rememberMeServices())
                );

        http.sessionManagement((sessionManagement) -> sessionManagement
                        .maximumSessions(2)
                        .sessionRegistry(sessionRegistry())
                );


        http.authorizeHttpRequests(customizer -> {
            customizer
                    .requestMatchers("/js/**").permitAll()
                    .requestMatchers("/img/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/fonts/**").permitAll()
                    .requestMatchers("/static/lib/**").permitAll()
                    .requestMatchers("/static/scss/**").permitAll()
                    //Producto security
                    .requestMatchers(HttpMethod.GET, "/producto/list").hasAnyRole("ADMIN", "EMPLEADO")
                    .requestMatchers(HttpMethod.GET, "/producto/nuevo").hasAnyRole("ADMIN", "EMPLEADO")
                    .requestMatchers(HttpMethod.POST, "/producto/nuevo").hasAnyRole("ADMIN", "EMPLEADO")
                    .requestMatchers(HttpMethod.POST, "/producto/**").hasRole("ADMIN")
                    //Dietas security
                    .requestMatchers(HttpMethod.GET, "/dietaUsuario/list").hasAnyRole("ADMIN", "EMPLEADO")
                    .requestMatchers(HttpMethod.GET, "/dietaUsuario/nuevo").hasAnyRole("ADMIN", "EMPLEADO")
                    .requestMatchers(HttpMethod.POST, "/dietaUsuario/nuevo").hasAnyRole("ADMIN", "EMPLEADO")
                    .requestMatchers(HttpMethod.POST, "/dietaUsuario/**").hasRole("ADMIN")
                    .requestMatchers("/index").permitAll(); // Permitir todas las solicitudes por defecto
            customizer.anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices =
                new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public SpringSessionBackedSessionRegistry<Session> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }
}