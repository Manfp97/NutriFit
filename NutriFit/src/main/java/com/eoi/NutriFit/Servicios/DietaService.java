package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;

public class DietaService extends AbstractBusinessService <Dieta, Integer, DietaRepo>{
    protected DietaService(DietaRepo dietaRepo) {
        super(dietaRepo);
    }
}
