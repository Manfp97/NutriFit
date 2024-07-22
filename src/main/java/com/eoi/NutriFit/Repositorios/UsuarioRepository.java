package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findBynombreDeUsuario(String nombreDeUsuario);
}
