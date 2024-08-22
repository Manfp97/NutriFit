package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los usuarios.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio relacionados con la gestión de usuarios.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class UsuarioServi extends AbstractBusinessService<Usuario, Integer, UsuarioRepository> {

    /**
     * Constructor que inyecta el repositorio de usuarios.
     *
     * @param usuarioRepository El repositorio de usuarios.
     */
    protected UsuarioServi(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }

    /**
     * Busca un usuario por su nombre de usuario, asegurando que esté activo.
     *
     * @param username El nombre de usuario a buscar.
     * @return El usuario encontrado, si existe y está activo.
     * @throws EntityNotFoundException Si el usuario no se encuentra o no está activo.
     */
    public Usuario findByUsername(String username) {
        return getRepo().findUsuarioByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }
}