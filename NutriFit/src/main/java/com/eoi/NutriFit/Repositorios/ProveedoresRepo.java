package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedoresRepo extends JpaRepository <Proveedores, Integer> {
}
