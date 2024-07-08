package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.ClienteEntrenamiento;
import com.eoi.NutriFit.Repositorios.ClienteEntrenamientoRepo;
import org.springframework.stereotype.Service;

@Service
public class ClienteEntrenamientoServi extends AbstractBusinessService<ClienteEntrenamiento, Integer, ClienteEntrenamientoRepo>{
    protected ClienteEntrenamientoServi(ClienteEntrenamientoRepo clienteEntrenamientoRepo) {
        super(clienteEntrenamientoRepo);
    }
}
