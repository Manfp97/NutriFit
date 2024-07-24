package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenamientoRepo extends EntrenamientoPaginAndSortingRepository<Entrenamiento, Integer> {
    Page<Entrenamiento> findByCategoriaAndDificultad(String categoria, String dificultad, Pageable pageable);
    Page<Entrenamiento> findByCategoria(String categoria, Pageable pageable);
    Page<Entrenamiento> findByDificultad(String dificultad, Pageable pageable);
    Page<Entrenamiento> findAll(Pageable pageable);
}
