package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends JpaRepository <Usuario, Integer> {
}
