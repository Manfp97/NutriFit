package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesEntrenamientoRepo extends JpaRepository<DetallesEntrenamiento, Integer> {
}
