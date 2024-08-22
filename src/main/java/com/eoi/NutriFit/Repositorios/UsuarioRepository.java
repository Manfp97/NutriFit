package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad {@link Usuario}.
 * <p>
 * Este repositorio extiende {@link UsuarioPagingAndSorting} y proporciona métodos
 * adicionales específicos para gestionar {@link Usuario}. Incluye operaciones de
 * búsqueda basadas en el nombre de usuario y el estado de activación, así como
 * la capacidad de recuperar usuarios por rol con soporte para paginación.
 * </p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @version 1.0
 * @since 2024
 *
 * @see UsuarioPagingAndSorting
 * @see Usuario
 * @see Roles
 */
@Repository
public interface UsuarioRepository extends UsuarioPagingAndSorting<Usuario, Integer> {

    /**
     * Encuentra un {@link Usuario} basado en el nombre de usuario y que esté activo.
     *
     * @param username el nombre de usuario del {@link Usuario} a buscar.
     * @return un {@link Optional} que contiene el {@link Usuario} si se encuentra, o vacío si no se encuentra.
     */
    Optional<Usuario> findUsuarioByUsernameAndActivoTrue(String username);

    /**
     * Recupera todos los {@link Usuario} con soporte para paginación.
     *
     * @param pageable el objeto {@link Pageable} que define la paginación y el orden.
     * @return una página de {@link Usuario} que corresponde a la solicitud de paginación.
     */
    Page<Usuario> findAll(Pageable pageable);

    /**
     * Recupera todos los {@link Usuario} que tienen un rol específico, con soporte para paginación.
     *
     * @param rol el {@link Roles} por el cual filtrar los {@link Usuario}.
     * @param pageable el objeto {@link Pageable} que define la paginación y el orden.
     * @return una página de {@link Usuario} que corresponde a la solicitud de filtrado por rol y paginación.
     */
    Page<Usuario> findByRol(Roles rol, Pageable pageable);

}
