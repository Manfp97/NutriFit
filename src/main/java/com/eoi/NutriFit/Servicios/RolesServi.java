package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Repositorios.RolesRepo;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los roles de usuario.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio relacionados con la gestión de roles.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class RolesServi extends AbstractBusinessService<Roles, Integer, RolesRepo> {

    /**
     * Constructor que inyecta el repositorio de roles.
     *
     * @param rolesRepo El repositorio de roles.
     */
    protected RolesServi(RolesRepo rolesRepo) {
        super(rolesRepo);
    }
}