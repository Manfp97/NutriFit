package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Repositorios.ProgresionesEntrenamientoRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public class ProgresionesEntrenamientoServi extends AbstractBusinessService<ProgresionesEntrenamiento, Integer, ProgresionesEntrenamientoRepo> {

    protected ProgresionesEntrenamientoServi(ProgresionesEntrenamientoRepo progresionesEntrenamientoRepo) {
        super(progresionesEntrenamientoRepo);
    }
}
