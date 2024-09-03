package com.eoi.NutriFit.Repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Repositorio JPA personalizado que extiende JpaRepository, proporcionando funcionalidades adicionales de paginación y ordenación
 * específicamente diseñadas para entidades relacionadas con entrenamientos.
 *
 * Esta interfaz sirve como base para otros repositorios que requieran un control más preciso sobre la recuperación de datos de entrenamientos,
 * permitiendo realizar consultas paginadas y ordenadas de forma eficiente.
 *
 * La anotación @NoRepositoryBean evita que Spring Data cree una implementación concreta de este repositorio,
 * ya que está pensado para ser extendido por otros repositorios más específicos.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @param <T> El tipo de entidad de entrenamiento que representa este repositorio.
 * @param <ID> El tipo del identificador de la entidad de entrenamiento.
 */
@NoRepositoryBean
public interface EntrenamientoPaginAndSortingRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * Devuelve una lista de todas las entidades de entrenamiento, ordenadas según el criterio especificado.
     *
     * @param sort El criterio de ordenación a aplicar.
     * @return Una lista de todas las entidades de entrenamiento ordenadas.
     */
    List<T> findAll(Sort sort);

    /**
     * Devuelve una página de entidades de entrenamiento, paginada y ordenada según los criterios especificados.
     *
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de entidades de entrenamiento.
     */
    Page<T> findAll(Pageable pageable);
}