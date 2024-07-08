package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import com.eoi.NutriFit.Repositorios.ProgresionDietaRepo;
import org.springframework.stereotype.Service;

@Service
public class ProgresionDietaService extends AbstractBusinessService <ProgresionDieta, Integer, ProgresionDietaRepo>{
    protected ProgresionDietaService(ProgresionDietaRepo progresionDietaRepo) {
        super(progresionDietaRepo);
    }
}
