package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad DetallesEntrenamiento.
 *
 * Esta interfaz extiende JpaRepository, proporcionando automáticamente métodos CRUD (Create, Read, Update, Delete)
 * para la entidad DetallesEntrenamiento, así como métodos de consulta personalizados.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface DetallesEntrenamientoRepo extends JpaRepository<DetallesEntrenamiento, Integer> {

}