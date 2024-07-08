package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;
import org.springframework.stereotype.Service;

@Service
public class DietaService extends AbstractBusinessService <Dieta, Integer, DietaRepo>{
    protected DietaService(DietaRepo dietaRepo) {
        super(dietaRepo);
    }
}
