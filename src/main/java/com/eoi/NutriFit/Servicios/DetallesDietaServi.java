package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import com.eoi.NutriFit.Repositorios.DetallesDietaRepo;
import org.springframework.stereotype.Service;

@Service
public class DetallesDietaServi extends AbstractBusinessService<DetallesDieta, Integer, DetallesDietaRepo>{
    protected DetallesDietaServi(DetallesDietaRepo detallesDietaRepo) {
        super(detallesDietaRepo);
    }
}
