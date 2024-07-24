package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Dieta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DietaRepo extends DietaPagingAndSortingRepository<Dieta, Integer> {
    Page<Dieta> findByCategoria(String categoria, Pageable pageable);
    Page<Dieta> findAll(Pageable pageable);
}