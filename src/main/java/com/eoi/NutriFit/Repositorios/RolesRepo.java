package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Roles.
 *
 * Esta interfaz proporciona métodos básicos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades de tipo Roles.
 * Al extender JpaRepository, se heredan métodos predefinidos para gestionar el ciclo de vida de las entidades en la base de datos.
 *
 * Además, ofrece un método personalizado para buscar un rol por su nombre, facilitando la recuperación de roles específicos.
 *
 * Esta interfaz es el punto de acceso a la capa de datos para interactuar con la información de los roles.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

    /**
     * Busca un rol por su nombre.
     *
     * Este método personalizado permite encontrar un rol de forma eficiente utilizando su nombre como criterio de búsqueda.
     *
     * @param nombre El nombre del rol a buscar.
     * @return El rol encontrado, o null si no existe.
     */
    Roles findByNombreRol(String nombre);
}