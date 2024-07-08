package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.DetallesCliente;
import com.eoi.NutriFit.Repositorios.DetallesClientesRepo;
import org.springframework.stereotype.Service;

@Service
public class DetallesClientesServi extends AbstractBusinessService<DetallesCliente, Integer, DetallesClientesRepo>{
    protected DetallesClientesServi(DetallesClientesRepo detallesClientesRepo) {
        super(detallesClientesRepo);
    }
}
