package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServi extends AbstractBusinessService<Usuario, Integer, UsuarioRepository> {

    protected UsuarioServi(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
    }


}


