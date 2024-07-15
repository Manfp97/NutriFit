package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.ClienteDieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDietaRepo extends JpaRepository<ClienteDieta, Integer> {

}
