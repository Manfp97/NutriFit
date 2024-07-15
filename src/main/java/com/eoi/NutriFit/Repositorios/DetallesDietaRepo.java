package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesDietaRepo extends JpaRepository<DetallesDieta, Integer> {

}
