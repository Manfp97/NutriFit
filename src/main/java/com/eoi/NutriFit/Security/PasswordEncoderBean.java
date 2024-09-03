package com.eoi.NutriFit.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración de Spring para el codificador de contraseñas BCrypt.
 *
 * Esta clase define un bean de tipo `BCryptPasswordEncoder` que será utilizado por la aplicación
 * para codificar y comparar contraseñas de forma segura.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Configuration
public class PasswordEncoderBean {

    /**
     * Crea un bean de tipo `BCryptPasswordEncoder`.
     *
     * Este bean será utilizado por la aplicación para codificar y comparar contraseñas de forma segura.
     * El uso de BCryptPasswordEncoder garantiza que las contraseñas se almacenen de forma hash,
     * haciendo que sean difíciles de descifrar en caso de una brecha de seguridad.
     *
     * @return Una instancia de `BCryptPasswordEncoder`.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}