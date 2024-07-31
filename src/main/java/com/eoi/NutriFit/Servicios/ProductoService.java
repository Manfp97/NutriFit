package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends AbstractBusinessService <Producto, Integer, ProductoRepo>{
    private final ProductoRepo productoRepo;

    protected ProductoService(ProductoRepo productoRepo) {
        super(productoRepo);
        this.productoRepo = productoRepo;
    }

    @Override
    public void eliminarPorId(Integer id) {
        if (getRepo().existsById(id)) {
            //getRepo().deleteById(id);
            productoRepo.delete(getRepo().getById(id));

        } else {
            throw new EntityNotFoundException("Entidad con id " + id + " no encontrada.");
        }
    }
}
