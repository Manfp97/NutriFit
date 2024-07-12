package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Carrito;
import com.eoi.NutriFit.Repositorios.CarritoRepo;

public class CarritoServi extends AbstractBusinessService<Carrito, Integer, CarritoRepo>{
    protected CarritoServi(CarritoRepo carritoRepo) {
        super(carritoRepo);
    }
}
