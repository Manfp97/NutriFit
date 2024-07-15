package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.SesionColectiva;
import com.eoi.NutriFit.Repositorios.SesionColectivaRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SesionColectivaServi extends AbstractBusinessService<SesionColectiva, Integer, SesionColectivaRepo> {

    protected SesionColectivaServi(SesionColectivaRepo sesionColectivaRepo) {
        super(sesionColectivaRepo);
    }


}
