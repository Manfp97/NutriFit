package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Entrenamiento, que extiende las funcionalidades de paginación y ordenación.
 *
 * Esta interfaz proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades de tipo Entrenamiento,
 * así como métodos personalizados para buscar entrenamientos por categoría, dificultad o una combinación de ambas, y para realizar consultas paginadas y ordenadas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface EntrenamientoRepo extends EntrenamientoPaginAndSortingRepository<Entrenamiento, Integer> {

    /**
     * Encuentra una página de entrenamientos que pertenecen a una categoría y tienen un nivel de dificultad específico.
     *
     * @param categoria La categoría de los entrenamientos a buscar.
     * @param dificultad El nivel de dificultad de los entrenamientos a buscar.
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de entrenamientos que cumplen con los criterios de búsqueda.
     */
    Page<Entrenamiento> findByCategoriaAndDificultad(String categoria, String dificultad, Pageable pageable);

    /**
     * Encuentra una página de entrenamientos que pertenecen a una categoría específica.
     *
     * @param categoria La categoría de los entrenamientos a buscar.
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de entrenamientos que cumplen con el criterio de búsqueda.
     */
    Page<Entrenamiento> findByCategoria(String categoria, Pageable pageable);

    /**
     * Encuentra una página de entrenamientos que tienen un nivel de dificultad específico.
     *
     * @param dificultad El nivel de dificultad de los entrenamientos a buscar.
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     *     @return Una página de entrenamientos que cumplen con el criterio de búsqueda.
     */
    Page<Entrenamiento> findByDificultad(String dificultad, Pageable pageable);

    /**
     * Encuentra todas las entrenamientos, paginadas y ordenadas según los criterios especificados.
     *
     * Este método hereda de la interfaz base `EntrenamientoPaginAndSortingRepository`.
     *
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de todas los entrenamientos.
     */
    Page<Entrenamiento> findAll(Pageable pageable);
}