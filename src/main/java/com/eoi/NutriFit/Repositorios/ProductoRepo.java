package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepo extends ProductoPagingAndSorting<Producto, Integer> {
    Page<Producto> findAll(Pageable pageable);
    Page<Producto> findByCategoria(String nombre, Pageable pageable);
}
