package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ProgresionDieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgresionDietaRepo extends JpaRepository<ProgresionDieta, Integer> {
}