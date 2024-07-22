package com.eoi.NutriFit.configuration;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationStartup(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (usuarioRepository.count() == 0) {
            List<Usuario> usuarios = Arrays.asList(
                    new Usuario("user1", bCryptPasswordEncoder.encode("password")),
                    new Usuario("user2", bCryptPasswordEncoder.encode("password"))
                    // add more users as needed
            );

            usuarioRepository.saveAll(usuarios);
            logger.info("Default users have been created.");
        } else {
            logger.info("Users already exist. Skipping initialization.");
        }
    }
}
