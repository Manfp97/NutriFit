package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Producto, que extiende las funcionalidades de paginación y ordenación.
 *
 * Esta interfaz proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades de tipo Producto,
 * así como métodos personalizados para buscar productos por categoría y para realizar consultas paginadas y ordenadas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface ProductoRepo extends ProductoPagingAndSorting<Producto, Integer> {

    /**
     * Encuentra todos los productos, paginados y ordenados según los criterios especificados.
     *
     * Este método hereda de la interfaz base `ProductoPagingAndSorting`.
     *
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de todos los productos.
     */
    Page<Producto> findAll(Pageable pageable);

    /**
     * Encuentra una página de productos que pertenecen a una categoría específica.
     *
     * @param nombre El nombre de la categoría de los productos a buscar.
     * @param pageable Un objeto Pageable que encapsula la información de paginación y ordenación.
     * @return Una página de productos que cumplen con el criterio de búsqueda.
     */
    Page<Producto> findByCategoria(String nombre, Pageable pageable);
}