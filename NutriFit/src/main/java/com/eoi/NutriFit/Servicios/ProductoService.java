package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;

public class ProductoService extends AbstractBusinessService <Producto, Integer, ProductoRepo>{
    protected ProductoService(ProductoRepo productoRepo) {
        super(productoRepo);
    }
}
