package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import com.eoi.NutriFit.Repositorios.DetallesEntrenamientoRepo;

public class DetallesEntrenamientoService extends AbstractBusinessService <DetallesEntrenamiento, Integer, DetallesEntrenamientoRepo>{
    protected DetallesEntrenamientoService(DetallesEntrenamientoRepo detallesEntrenamientoRepo) {
        super(detallesEntrenamientoRepo);
    }
}
