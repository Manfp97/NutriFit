package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad ProgresiónDieta.
 *
 * Esta interfaz proporciona métodos básicos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades de tipo ProgresiónDieta.
 * Al extender JpaRepository, se heredan métodos predefinidos para gestionar el ciclo de vida de las entidades en la base de datos.
 *
 * Esta interfaz es el punto de acceso a la capa de datos para interactuar con las progresiones de las dietas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface ProgresionDietaRepo extends JpaRepository<ProgresionDieta, Integer> {
}