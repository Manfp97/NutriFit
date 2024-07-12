package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgresionesEntrenamientoRepo extends JpaRepository <ProgresionesEntrenamiento, Integer> {
}
