package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Repositorios.EntrenamientoRepo;

public class EntrenamientoService extends AbstractBusinessService <Entrenamiento, Integer, EntrenamientoRepo>{
    protected EntrenamientoService(EntrenamientoRepo entrenamientoRepo) {
        super(entrenamientoRepo);
    }
}
