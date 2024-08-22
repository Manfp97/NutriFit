package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Proveedores;
import com.eoi.NutriFit.Repositorios.ProveedoresRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los proveedores.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio relacionados con la gestión de proveedores.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class ProveedoresServi extends AbstractBusinessService<Proveedores, Integer, ProveedoresRepo> {

    /**
     * Constructor que inyecta el repositorio de proveedores.
     *
     * @param proveedoresRepo El repositorio de proveedores.
     */
    protected ProveedoresServi(ProveedoresRepo proveedoresRepo) {
        super(proveedoresRepo);
    }
}