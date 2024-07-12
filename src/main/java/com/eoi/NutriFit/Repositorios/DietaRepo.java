package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietaRepo extends JpaRepository<Dieta, Integer> {
}
