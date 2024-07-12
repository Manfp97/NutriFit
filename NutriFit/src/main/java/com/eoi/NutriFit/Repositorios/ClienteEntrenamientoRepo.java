package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ClienteEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteEntrenamientoRepo extends JpaRepository<ClienteEntrenamiento, Integer> {

}
