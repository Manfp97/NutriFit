package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con la entidad {@link Usuario}.
 * <p>
 * Este servicio extiende de {@link AbstractBusinessService} y proporciona métodos para
 * encontrar usuarios por su nombre de usuario, considerando solo aquellos usuarios activos.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Service
public class UsuarioServi extends AbstractBusinessService<Usuario, Integer, UsuarioRepository> {

    /**
     * Constructor del servicio que inyecta el repositorio de usuarios.
     *
     * @param usuarioRepository El repositorio de usuarios que se utilizará para acceder a los datos.
     */
    protected UsuarioServi(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }

    /**
     * Encuentra un usuario activo por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario que se desea encontrar.
     * @return El usuario correspondiente al nombre de usuario dado.
     * @throws EntityNotFoundException Si el usuario no se encuentra o no está activo.
     */
    public Usuario findByUsername(String username) {
        return getRepo().findUsuarioByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    /**
     * Busca un usuario activo por su nombre de usuario y devuelve un {@link Optional}.
     *
     * @param username El nombre de usuario del usuario que se desea buscar.
     * @return Un {@link Optional} que contiene el usuario si se encuentra, o vacío si no se encuentra.
     */
    public Optional<Usuario> encuentraPorUsername(String username) {
        return getRepo().findUsuarioByUsernameAndActivoTrue(username);
    }
}
