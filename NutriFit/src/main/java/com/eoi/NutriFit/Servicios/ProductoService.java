package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends AbstractBusinessService <Producto, Integer, ProductoRepo>{
    protected ProductoService(ProductoRepo productoRepo) {
        super(productoRepo);
    }
}
