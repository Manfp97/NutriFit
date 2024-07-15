package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Servicios.ProgresionesEntrenamientoServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/progresionesEntrenamiento")
public class ProgresionesEntrenamientoController {

    @Autowired
    private ProgresionesEntrenamientoServi service;

    @GetMapping
    public String getAll(Model model) {
        List<ProgresionesEntrenamiento> listaProgresiones = service.buscarEntidades();
        model.addAttribute("progresionesEntrenamiento", listaProgresiones);
        return "progresionesEntrenamiento";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresionesEntrenamiento> getById(@PathVariable Integer id) {
        Optional<ProgresionesEntrenamiento> progresionEntrenamiento = service.encuentraPorId(id);
        if (progresionEntrenamiento.isPresent()) {
            return ResponseEntity.ok(progresionEntrenamiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ProgresionesEntrenamiento create(@RequestBody ProgresionesEntrenamientoServi progresionEntrenamiento) throws Exception {
        return service.guardar(new ProgresionesEntrenamiento());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresionesEntrenamiento> update(@PathVariable Integer id, @RequestBody ProgresionesEntrenamiento progresionEntrenamiento) throws Exception {
        Optional<ProgresionesEntrenamiento> existingProgresionEntrenamiento = service.encuentraPorId(id);
        if (existingProgresionEntrenamiento.isPresent()) {
            progresionEntrenamiento.setId(id);
            return ResponseEntity.ok(service.guardar(progresionEntrenamiento));
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

