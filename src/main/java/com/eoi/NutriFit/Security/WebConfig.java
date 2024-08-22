package com.eoi.NutriFit.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para permitir solicitudes desde orígenes permitidos.
 *
 * Esta clase configura las políticas de CORS (Cross-Origin Resource Sharing) para permitir que
 * aplicaciones de diferentes orígenes (como aplicaciones front-end) se comuniquen con esta aplicación.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Agrega mapeos CORS para permitir solicitudes desde los orígenes especificados.
     *
     * Este método configura las políticas de CORS para todas las rutas ("/**") y permite los
     * métodos HTTP más comunes (GET, POST, PUT, DELETE, OPTIONS). También permite el envío de credenciales.
     *
     * @param registry El registro de CORS para agregar mapeos.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8091", "http://localhost:3000", "https://3d52-46-6-26-199.ngrok-free.app/") // Agrega la URL de ngrok aquí
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}