package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.UsuarioDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDemo, Integer> {

    Optional<UsuarioDemo> findBynombreDeUsuario(String username);

}