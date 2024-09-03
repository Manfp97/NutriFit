package com.eoi.NutriFit.Repositorios;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Interfaz de repositorio que extiende {@link JpaRepository} para proporcionar
 * funcionalidad adicional de paginación y ordenación.
 * <p>
 * Esta interfaz proporciona métodos para recuperar entidades con soporte para
 * ordenación y paginación. Está diseñada para ser extendida por otros repositorios
 * específicos de entidades que requieran estas capacidades.
 * </p>
 * <p>
 * La interfaz está marcada con {@link NoRepositoryBean}, lo que indica que no debe
 * ser instanciada directamente y sirve como una interfaz base para otros repositorios.
 * </p>
 *
 * @param <T> el tipo de entidad manejada por el repositorio.
 * @param <ID> el tipo del identificador de la entidad.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @version 1.0
 * @since 2024
 *
 * @see JpaRepository
 * @see Pageable
 * @see Sort
 * @see Page
 */
@NonNullApi
@NoRepositoryBean
public interface UsuarioPagingAndSorting<T, ID> extends JpaRepository<T, ID> {

    /**
     * Recupera todas las entidades ordenadas según el parámetro {@link Sort}.
     *
     * @param sort el objeto {@link Sort} que define el criterio de ordenación.
     * @return una lista de todas las entidades ordenadas.
     */
    List<T> findAll(Sort sort);

    /**
     * Recupera todas las entidades paginadas según el parámetro {@link Pageable}.
     *
     * @param pageable el objeto {@link Pageable} que define la paginación y el orden.
     * @return una página de entidades que corresponde a la solicitud de paginación.
     */
    Page<T> findAll(Pageable pageable);

}
