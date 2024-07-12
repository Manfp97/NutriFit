package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.SesionClientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionClientesRepo extends JpaRepository <SesionClientes, Integer> {
}
