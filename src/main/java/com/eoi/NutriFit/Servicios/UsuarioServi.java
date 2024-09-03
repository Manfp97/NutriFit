package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioServi extends AbstractBusinessService<Usuario, Integer, UsuarioRepository> {

    protected UsuarioServi(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }

    public Usuario findByUsername(String username) {
        return getRepo().findUsuarioByUsernameAndActivoTrue(username).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public Optional<Usuario> encuentraPorUsername(String username) {
        return getRepo().findUsuarioByUsernameAndActivoTrue(username);
    }
}

