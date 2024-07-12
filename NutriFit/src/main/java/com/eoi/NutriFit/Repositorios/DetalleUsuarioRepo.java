package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleUsuarioRepo extends JpaRepository<DetalleUsuario, Integer> {
}
