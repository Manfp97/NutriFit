package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import com.eoi.NutriFit.Repositorios.ProgresionDietaRepo;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con la progresión de las dietas.
 * Extiende la clase base `AbstractBusinessService` para proporcionar funcionalidades comunes
 * a todos los servicios de negocio.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class ProgresionDietaService extends AbstractBusinessService<ProgresionDieta, Integer, ProgresionDietaRepo> {

    /**
     * Constructor que inyecta el repositorio de progresión de dietas.
     *
     * @param progresionDietaRepo El repositorio de progresión de dietas.
     */
    protected ProgresionDietaService(ProgresionDietaRepo progresionDietaRepo) {
        super(progresionDietaRepo);
    }
}