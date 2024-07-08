package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioServi extends AbstractBusinessService<Usuario, Integer, UsuarioRepo> {

    protected UsuarioServi(UsuarioRepo usuarioRepo) {
        super(usuarioRepo);
    }

    public static void registrarUsuario(Usuario usuario) {

    }
}


