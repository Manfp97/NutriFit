package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenamientoRepo extends JpaRepository<Entrenamiento, Integer> {
}
