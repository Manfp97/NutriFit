package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionColectivaRepo extends JpaRepository <SesionColectiva, Integer> {
}
