package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {
}
