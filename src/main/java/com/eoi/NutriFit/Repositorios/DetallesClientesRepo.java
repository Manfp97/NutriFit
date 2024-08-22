package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad DetallesCliente.
 *
 * Esta interfaz extiende JpaRepository, proporcionando automáticamente métodos CRUD (Create, Read, Update, Delete)
 * para la entidad DetallesCliente, así como métodos de consulta personalizados.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface DetallesClientesRepo extends JpaRepository<DetallesCliente, Integer> {

}