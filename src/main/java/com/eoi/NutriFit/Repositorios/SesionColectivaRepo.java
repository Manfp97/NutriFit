package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para la entidad {@link SesionColectiva}.
 * <p>
 * Este repositorio permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la entidad {@link SesionColectiva}. Extiende la interfaz {@link JpaRepository},
 * la cual proporciona una serie de métodos predefinidos para manejar la persistencia
 * de datos en la base de datos.
 * </p>
 * <p>
 * La implementación de esta interfaz es gestionada automáticamente por el contenedor
 * de Spring, facilitando la integración con el contexto de persistencia de JPA.
 * </p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @version 1.0
 * @since 2024
 *
 * @see JpaRepository
 * @see SesionColectiva
 */
@Repository
public interface SesionColectivaRepo extends JpaRepository<SesionColectiva, Integer> {

}
