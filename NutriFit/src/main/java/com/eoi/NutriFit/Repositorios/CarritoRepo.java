package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepo extends JpaRepository<Carrito, Integer> {

}
