package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad {@link Usuario}.
 * <p>
 * Este repositorio proporciona métodos para realizar operaciones de persistencia en la base de datos
 * relacionadas con la entidad {@code Usuario}. Extiende {@link JpaRepository} para aprovechar
 * funcionalidades básicas de JPA y {@link UsuarioPagingAndSorting} para soporte adicional de paginación y ordenación.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Repository
public interface UsuarioRepository extends UsuarioPagingAndSorting<Usuario, Integer> {

    /**
     * Encuentra un usuario por su nombre de usuario y verifica que esté activo.
     *
     * @param username el nombre de usuario del usuario a buscar
     * @return un {@link Optional} que contiene el usuario si se encuentra y está activo, o vacío si no se encuentra
     */
    Optional<Usuario> findUsuarioByUsernameAndActivoTrue(String username);

    /**
     * Obtiene una página de usuarios.
     *
     * @param pageable la información de paginación y ordenación
     * @return una {@link Page} de usuarios
     */
    Page<Usuario> findAll(Pageable pageable);

    /**
     * Obtiene una página de usuarios filtrados por rol.
     *
     * @param rol el rol para filtrar los usuarios
     * @param pageable la información de paginación y ordenación
     * @return una {@link Page} de usuarios que tienen el rol especificado
     */
    Page<Usuario> findByRol(Roles rol, Pageable pageable);

    /**
     * Encuentra un usuario por su token de reinicio.
     *
     * @param resetToken el token de reinicio del usuario a buscar
     * @return un {@link Optional} que contiene el usuario si se encuentra, o vacío si no se encuentra
     */
    Optional<Usuario> findByResetToken(String resetToken);

    /**
     * Encuentra un usuario activo por su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del usuario a buscar
     * @return un {@link Optional} que contiene el usuario activo si se encuentra, o vacío si no se encuentra
     */
    @Query("SELECT u FROM Usuario u JOIN u.detalleUsuario d WHERE d.email = :email AND u.activo = true")
    Optional<Usuario> findActiveUserByEmail(@Param("email") String email);
}
