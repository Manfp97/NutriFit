package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import com.eoi.NutriFit.Repositorios.SesionColectivaRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con las sesiones colectivas.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio relacionados con la gestión de sesiones colectivas.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class SesionColectivaServi extends AbstractBusinessService<SesionColectiva, Integer, SesionColectivaRepo> {

    /**
     * Constructor que inyecta el repositorio de sesiones colectivas.
     *
     * @param sesionColectivaRepo El repositorio de sesiones colectivas.
     */
    protected SesionColectivaServi(SesionColectivaRepo sesionColectivaRepo) {
        super(sesionColectivaRepo);
    }
}