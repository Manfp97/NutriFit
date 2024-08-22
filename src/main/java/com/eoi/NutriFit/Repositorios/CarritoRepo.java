package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Carrito.
 *
 * Esta interfaz extiende JpaRepository, proporcionando automáticamente métodos CRUD (Create, Read, Update, Delete)
 * para la entidad Carrito, así como métodos de consulta personalizados.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface CarritoRepo extends JpaRepository<Carrito, Integer> {

}