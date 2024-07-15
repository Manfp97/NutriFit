package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.SesionClientes;
import com.eoi.NutriFit.Servicios.SesionClientesServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sesionesClientes")
public class SesionClientesController {

    @Autowired
    private SesionClientesServi service;

    @GetMapping
    public String getAll(Model model) {
        List<SesionClientes> listaSesiones = service.buscarEntidades();
        model.addAttribute("sesionClientes", listaSesiones);
        return "sesionClientes";
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionClientes> getById(@PathVariable Integer id) {
        Optional<SesionClientes> sesionCliente = service.encuentraPorId(id);
        if (sesionCliente.isPresent()) {
            return ResponseEntity.ok(sesionCliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public SesionClientes create(@RequestBody SesionClientes  sesionCliente) throws Exception {
        SesionClientes sesionClientes = null;
        return service.guardar(sesionClientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SesionClientes> update(@PathVariable Integer id, @RequestBody SesionClientes sesionCliente) throws Exception {
        Optional<SesionClientes> existingSesionCliente = service.encuentraPorId(id);
        if (existingSesionCliente.isPresent()) {
            sesionCliente.setId(id);
            return ResponseEntity.ok(service.guardar(sesionCliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.encuentraPorId(id).isPresent()) {
            service.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

