package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.SesionClientes;
import com.eoi.NutriFit.Repositorios.SesionClientesRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SesionClientesServi extends AbstractBusinessService<SesionClientes, Integer, SesionClientesRepo> {

    protected SesionClientesServi(SesionClientesRepo sesionClientesRepo) {
        super(sesionClientesRepo);
    }


}
