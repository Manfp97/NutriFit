package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Repositorios.DetalleUsuarioRepo;

public class DetalleUsuarioService extends AbstractBusinessService <DetalleUsuario, Integer, DetalleUsuarioRepo>{
    protected DetalleUsuarioService(DetalleUsuarioRepo detalleUsuarioRepo) {
        super(detalleUsuarioRepo);
    }
}
