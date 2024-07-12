package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesClientesRepo  extends JpaRepository<DetallesCliente,Integer> {
}
