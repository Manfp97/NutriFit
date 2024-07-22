package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioPagingAndSortingRepository extends PagingAndSortingRepository<Usuario, Integer> {

    Optional<Usuario> findByNombreUsuario(String username);

    Page<Usuario> findAllByAccountNonExpired(boolean status, Pageable pageable);

}