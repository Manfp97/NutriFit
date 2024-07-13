package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Proveedores;
import com.eoi.NutriFit.Repositorios.ProveedoresRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedoresServi extends AbstractBusinessService<Proveedores, Integer, ProveedoresRepo> {

    protected ProveedoresServi(ProveedoresRepo proveedoresRepo) {
        super(proveedoresRepo);
    }


}
