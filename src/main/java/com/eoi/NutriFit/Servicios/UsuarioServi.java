package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioPagingAndSortingRepository;
import com.eoi.NutriFit.Repositorios.UsuarioRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServi extends AbstractBusinessService<Usuario, Integer, UsuarioRepo> {

    private final UsuarioPagingAndSortingRepository usuarioPagingAndSortingRepository;

    // Constructor with dependency injection
    public UsuarioServi(UsuarioRepo usuarioRepo, UsuarioPagingAndSortingRepository usuarioPagingAndSortingRepository) {
        super(usuarioRepo);
        this.usuarioPagingAndSortingRepository = usuarioPagingAndSortingRepository;
    }

    // Method to find paginated Usuario objects
    public Page<Usuario> findPaginated(Pageable pageable) {
        return usuarioPagingAndSortingRepository.findAll(pageable);
    }
}
