package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.SesionClientes;
import com.eoi.NutriFit.Repositorios.SesionClientesRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con las sesiones de los clientes.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio relacionados con la gestión de sesiones de clientes.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class SesionClientesServi extends AbstractBusinessService<SesionClientes, Integer, SesionClientesRepo> {

    /**
     * Constructor que inyecta el repositorio de sesiones de clientes.
     *
     * @param sesionClientesRepo El repositorio de sesiones de clientes.
     */
    protected SesionClientesServi(SesionClientesRepo sesionClientesRepo) {
        super(sesionClientesRepo);
    }
}