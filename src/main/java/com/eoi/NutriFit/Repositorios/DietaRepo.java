package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Dieta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Dieta, que extiende las funcionalidades de paginación y ordenación.
 *
 * Esta interfaz proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades de tipo Dieta,
 * así como métodos personalizados para buscar dietas por categoría y para realizar consultas paginadas y ordenadas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface DietaRepo extends DietaPagingAndSortingRepository<Dieta, Integer> {

    /**
     * Encuentra una página de dietas que pertenecen a una categoría específica.
     *
     * @param categoria La categoría de las dietas a buscar.
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de dietas que cumplen con el criterio de búsqueda.
     */
    Page<Dieta> findByCategoria(String categoria, Pageable pageable);

    /**
     * Encuentra todas las dietas, paginadas y ordenadas según los criterios especificados.
     *
     * Este método hereda de la interfaz base `DietaPagingAndSortingRepository`.
     *
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de todas las dietas.
     */
    Page<Dieta> findAll(Pageable pageable);
}