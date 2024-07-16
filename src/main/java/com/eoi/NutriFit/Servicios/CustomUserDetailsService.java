package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import com.eoi.NutriFit.Entidades.UsuarioDemo;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

/**
 * Servicio personalizado de autenticación de usuarios.
 * Implementa la interfaz {@link UserDetailsService} de Spring Security para proporcionar
 * una forma de recuperar los detalles del usuario desde la base de datos utilizando un nombre de usuario.
 *
 * <p>
 * Esta clase se marca con la anotación {@link Service} para indicar que es un componente de servicio
 * de Spring, y se puede inyectar en otros componentes mediante la inyección de dependencias.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final MessageSource messageSource;

    /**
     * Constructor de la clase que recibe un {@link UsuarioRepository} para interactuar con la base de datos
     * y un {@link MessageSource} para la internacionalización de mensajes.
     *
     * <p>
     * La inyección de dependencias es un patrón que permite que las dependencias de una clase sean
     * proporcionadas por el contenedor de Spring, en lugar de ser creadas manualmente dentro de la clase.
     * Esto facilita la gestión de las dependencias y mejora la capacidad de prueba y mantenimiento del código.
     * </p>
     *
     * @param usuarioRepository el repositorio de usuarios que se utilizará para recuperar los datos del usuario.
     * @param messageSource el componente de Spring utilizado para la internacionalización de mensajes.
     */
    public CustomUserDetailsService(UsuarioRepository usuarioRepository, MessageSource messageSource) {
        this.usuarioRepository = usuarioRepository;
        this.messageSource = messageSource;
    }


    /**
     * Sobrescribe el método {@link UserDetailsService#loadUserByUsername(String)} para cargar
     * los detalles del usuario desde la base de datos utilizando el nombre de usuario.
     *
     * @param username el nombre de usuario utilizado para buscar al usuario.
     * @return un objeto {@link UserDetails} que contiene la información del usuario.
     * @throws UsernameNotFoundException si no se encuentra ningún usuario con el nombre de usuario proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioDemo> usuarioDemo = usuarioRepository.findBynombreDeUsuario(username);
        if (usuarioDemo.isPresent()) {
            return usuarioDemo.get();
        } else {
            String errorMessage = messageSource.getMessage("user.not.found", null, Locale.getDefault());
            throw new UsernameNotFoundException(errorMessage );
        }

    }
}
