package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import com.eoi.NutriFit.Repositorios.DetallesDietaRepo;

public class DetallesDietaServi extends AbstractBusinessService<DetallesDieta, Integer, DetallesDietaRepo>{
    protected DetallesDietaServi(DetallesDietaRepo detallesDietaRepo) {
        super(detallesDietaRepo);
    }
}
