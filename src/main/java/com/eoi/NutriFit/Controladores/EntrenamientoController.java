package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Servicios.EntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrenamiento")
public class EntrenamientoController {

    @Autowired
    private EntrenamientoService service;

    @GetMapping
    public String getAll(Model model) {
        List<Entrenamiento> listaDetalles = service.buscarEntidades();
        model.addAttribute("entrenamientos", listaDetalles);
        return "entrenamientos";

    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrenamiento> getById(@PathVariable Integer id) {
        Optional<Entrenamiento> entrenamiento = service.encuentraPorId(id);
        if (entrenamiento.isPresent()) {
            return ResponseEntity.ok(entrenamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Entrenamiento create(@RequestBody Entrenamiento entrenamiento) throws Exception {
        return service.guardar(entrenamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenamiento> update(@PathVariable Integer id, @RequestBody Entrenamiento entrenamiento) throws Exception {
        Optional<Entrenamiento> existingEntrenamiento = service.encuentraPorId(id);
        if (existingEntrenamiento.isPresent()) {
            entrenamiento.setId(id);
            return ResponseEntity.ok(service.guardar(entrenamiento));
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