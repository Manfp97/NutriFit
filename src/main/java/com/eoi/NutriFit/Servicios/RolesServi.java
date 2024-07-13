package com.eoi.NutriFit.Servicios;

import Controladores.RolesController;
import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Repositorios.RolesRepo;
import org.springframework.stereotype.Service;

@Service
public class RolesServi extends AbstractBusinessService<Roles, Integer, RolesRepo> {

    protected RolesServi(RolesRepo rolesRepo) {
        super(rolesRepo);
    }


}
