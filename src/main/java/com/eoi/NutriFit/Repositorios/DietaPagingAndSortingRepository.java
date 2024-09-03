package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Repositorio JPA personalizado que extiende JpaRepository, proporcionando funcionalidades adicionales de paginación y ordenación.
 *
 * Esta interfaz sirve como base para otros repositorios que requieran un control más preciso sobre la recuperación de datos,
 * permitiendo realizar consultas paginadas y ordenadas de forma eficiente.
 *
 * La anotación @NonNullApi indica que todos los métodos de esta interfaz y sus clases relacionadas no aceptan argumentos nulos.
 * La anotación @NoRepositoryBean evita que Spring Data cree una implementación concreta de este repositorio,
 * ya que está pensado para ser extendido por otros repositorios.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @param <T> El tipo de entidad que representa este repositorio.
 * @param <ID> El tipo del identificador de la entidad.
 */
@NonNullApi
@NoRepositoryBean
public interface DietaPagingAndSortingRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * Devuelve una lista de todas las entidades, ordenadas según el criterio especificado.
     *
     * @param sort El criterio de ordenación a aplicar.
     * @return Una lista de todas las entidades ordenadas.
     */
    List<T> findAll(Sort sort);

    /**
     * Devuelve una página de entidades, paginada y ordenada según los criterios especificados.
     *
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de entidades.
     */
    Page<T> findAll(Pageable pageable);
}