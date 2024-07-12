package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetallesEntrenamiento;
import com.eoi.NutriFit.Servicios.DetallesEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detallesEntrenamiento")
public class DetallesEntrenamientoController {

    @Autowired
    private DetallesEntrenamientoService service;

    @GetMapping
    public String getAll(Model model) {
        List<DetallesEntrenamiento> listaDetalles = service.buscarEntidades();
        model.addAttribute("detallesEntrenamientos", listaDetalles);
        return "detallesEntrenamientos";

    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesEntrenamiento> getById(@PathVariable Integer id) {
        Optional<DetallesEntrenamiento> detallesEntrenamiento = service.encuentraPorId(id);
        if (detallesEntrenamiento.isPresent()) {
            return ResponseEntity.ok(detallesEntrenamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public DetallesEntrenamiento create(@RequestBody DetallesEntrenamiento detallesEntrenamiento) throws Exception {
        return service.guardar(detallesEntrenamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallesEntrenamiento> update(@PathVariable Integer id, @RequestBody DetallesEntrenamiento detallesEntrenamiento) throws Exception {
        Optional<DetallesEntrenamiento> existingDetallesEntrenamiento = service.encuentraPorId(id);
        if (existingDetallesEntrenamiento.isPresent()) {
            detallesEntrenamiento.setId(id);
            return ResponseEntity.ok(service.guardar(detallesEntrenamiento));
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