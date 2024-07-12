package com.eoi.NutriFit.Servicios;

import com.eoi.NutriFit.Entidades.Cliente;
import com.eoi.NutriFit.Repositorios.ClienteRepo;

public class ClienteServi extends AbstractBusinessService<Cliente, Integer, ClienteRepo>{
    protected ClienteServi(ClienteRepo clienteRepo) {
        super(clienteRepo);
    }
}
