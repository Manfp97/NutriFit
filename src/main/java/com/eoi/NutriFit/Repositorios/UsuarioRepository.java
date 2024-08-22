package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends UsuarioPagingAndSorting<Usuario, Integer> {

    Optional<Usuario>  findUsuarioByUsernameAndActivoTrue(String username);

    Page<Usuario> findAll(Pageable pageable);

    Page<Usuario> findByRol(Roles rol, Pageable pageable);

    Optional<Usuario> findByResetToken(String resetToken);

    @Query("SELECT u FROM Usuario u JOIN u.detalleUsuario d WHERE d.email = :email AND u.activo = true")
    Optional<Usuario> findActiveUserByEmail(@Param("email") String email);

}