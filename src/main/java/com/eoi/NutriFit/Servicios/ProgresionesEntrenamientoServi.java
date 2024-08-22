package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Repositorios.ProgresionesEntrenamientoRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con la progresión de los entrenamientos.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio relacionados con la progresión de las entidades.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class ProgresionesEntrenamientoServi extends AbstractBusinessService<ProgresionesEntrenamiento, Integer, ProgresionesEntrenamientoRepo> {

    /**
     * Constructor que inyecta el repositorio de progresiones de entrenamiento.
     *
     * @param progresionesEntrenamientoRepo El repositorio de progresiones de entrenamiento.
     */
    protected ProgresionesEntrenamientoServi(ProgresionesEntrenamientoRepo progresionesEntrenamientoRepo) {
        super(progresionesEntrenamientoRepo);
    }
}